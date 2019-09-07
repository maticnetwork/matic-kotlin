package network.matic.matick.core.protocol.rx;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import network.matic.matick.core.protocol.Web3j;
import network.matic.matick.core.protocol.core.DefaultBlockParameter;
import network.matic.matick.core.protocol.core.DefaultBlockParameterName;
import network.matic.matick.core.protocol.core.DefaultBlockParameterNumber;
import network.matic.matick.core.protocol.core.filters.BlockFilter;
import network.matic.matick.core.protocol.core.filters.Filter;
import network.matic.matick.core.protocol.core.filters.LogFilter;
import network.matic.matick.core.protocol.core.filters.PendingTransactionFilter;
import network.matic.matick.core.protocol.core.methods.response.EthBlock;
import network.matic.matick.core.protocol.core.methods.response.EthFilter;
import network.matic.matick.core.protocol.core.methods.response.Log;
import network.matic.matick.core.protocol.core.methods.response.Transaction;
import network.matic.matick.core.utils.Flowables;

/**
 * web3j reactive API implementation.
 */
public class JsonRpc2_0Rx {

    private final Web3j web3j;
    private final ScheduledExecutorService scheduledExecutorService;
    private final Scheduler scheduler;

    public JsonRpc2_0Rx(Web3j web3j, ScheduledExecutorService scheduledExecutorService) {
        this.web3j = web3j;
        this.scheduledExecutorService = scheduledExecutorService;
        this.scheduler = Schedulers.from(scheduledExecutorService);
    }

    public Flowable<String> ethBlockHashFlowable(long pollingInterval) {
        return Flowable.create(subscriber -> {
            BlockFilter blockFilter = new BlockFilter(
                    web3j, subscriber::onNext);
            run(blockFilter, subscriber, pollingInterval);
        }, BackpressureStrategy.BUFFER);
    }

    public Flowable<String> ethPendingTransactionHashFlowable(long pollingInterval) {
        return Flowable.create(subscriber -> {
            PendingTransactionFilter pendingTransactionFilter = new PendingTransactionFilter(
                    web3j, subscriber::onNext);

            run(pendingTransactionFilter, subscriber, pollingInterval);
        }, BackpressureStrategy.BUFFER);
    }

    public Flowable<Log> ethLogFlowable(
            network.matic.matick.core.protocol.core.methods.request.EthFilter ethFilter, long pollingInterval) {
        return Flowable.create(subscriber -> {
            LogFilter logFilter = new LogFilter(web3j, subscriber::onNext, ethFilter);
            run(logFilter, subscriber, pollingInterval);
        }, BackpressureStrategy.BUFFER);
    }


    private <T> void run(
            Filter<T> filter, FlowableEmitter<? super T> emitter,
            long pollingInterval) {

        filter.run(scheduledExecutorService, pollingInterval);
        emitter.setCancellable(filter::cancel);
    }

    public Flowable<Transaction> transactionFlowable(long pollingInterval) {
        return blockFlowable(true, pollingInterval)
                .flatMapIterable(JsonRpc2_0Rx::toTransactions);
    }

    public Flowable<Transaction> pendingTransactionFlowable(long pollingInterval) {
        return ethPendingTransactionHashFlowable(pollingInterval)
                .flatMap(transactionHash ->
                        web3j.ethGetTransactionByHash(transactionHash).flowable())
                .filter(ethTransaction -> ethTransaction.getTransaction().isPresent())
                .map(ethTransaction -> ethTransaction.getTransaction().get());
    }

    public Flowable<EthBlock> blockFlowable(
            boolean fullTransactionObjects, long pollingInterval) {
        return ethBlockHashFlowable(pollingInterval)
                .flatMap(blockHash ->
                        web3j.ethGetBlockByHash(blockHash, fullTransactionObjects).flowable());
    }

    public Flowable<EthBlock> replayBlocksFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock,
            boolean fullTransactionObjects) {
        return replayBlocksFlowable(startBlock, endBlock, fullTransactionObjects, true);
    }

    public Flowable<EthBlock> replayBlocksFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock,
            boolean fullTransactionObjects, boolean ascending) {
        // We use a scheduler to ensure this Flowable runs asynchronously for users to be
        // consistent with the other Flowables
        return replayBlocksFlowableSync(startBlock, endBlock, fullTransactionObjects, ascending)
                .subscribeOn(scheduler);
    }

    private Flowable<EthBlock> replayBlocksFlowableSync(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock,
            boolean fullTransactionObjects) {
        return replayBlocksFlowableSync(startBlock, endBlock, fullTransactionObjects, true);
    }

    private Flowable<EthBlock> replayBlocksFlowableSync(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock,
            boolean fullTransactionObjects, boolean ascending) {

        BigInteger startBlockNumber = null;
        BigInteger endBlockNumber = null;
        try {
            startBlockNumber = getBlockNumber(startBlock);
            endBlockNumber = getBlockNumber(endBlock);
        } catch (IOException e) {
            Flowable.error(e);
        }

        if (ascending) {
            return Flowables.range(startBlockNumber, endBlockNumber)
                    .flatMap(i -> web3j.ethGetBlockByNumber(
                            new DefaultBlockParameterNumber(i),
                            fullTransactionObjects).flowable());
        } else {
            return Flowables.range(startBlockNumber, endBlockNumber, false)
                    .flatMap(i -> web3j.ethGetBlockByNumber(
                            new DefaultBlockParameterNumber(i),
                            fullTransactionObjects).flowable());
        }
    }

    public Flowable<Transaction> replayTransactionsFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        return replayBlocksFlowable(startBlock, endBlock, true)
                .flatMapIterable(JsonRpc2_0Rx::toTransactions);
    }

    public Flowable<EthBlock> replayPastBlocksFlowable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects,
            Flowable<EthBlock> onCompleteFlowable) {
        // We use a scheduler to ensure this Flowable runs asynchronously for users to be
        // consistent with the other Flowables
        return replayPastBlocksFlowableSync(
                startBlock, fullTransactionObjects, onCompleteFlowable)
                .subscribeOn(scheduler);
    }

    public Flowable<EthBlock> replayPastBlocksFlowable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects) {
        return replayPastBlocksFlowable(
                startBlock, fullTransactionObjects, Flowable.empty());
    }

    private Flowable<EthBlock> replayPastBlocksFlowableSync(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects,
            Flowable<EthBlock> onCompleteFlowable) {

        BigInteger startBlockNumber;
        BigInteger latestBlockNumber;
        try {
            startBlockNumber = getBlockNumber(startBlock);
            latestBlockNumber = getLatestBlockNumber();
        } catch (IOException e) {
            return Flowable.error(e);
        }

        if (startBlockNumber.compareTo(latestBlockNumber) > -1) {
            return onCompleteFlowable;
        } else {
            return Flowable.concat(
                    replayBlocksFlowableSync(
                            new DefaultBlockParameterNumber(startBlockNumber),
                            new DefaultBlockParameterNumber(latestBlockNumber),
                            fullTransactionObjects),
                    Flowable.defer(() -> replayPastBlocksFlowableSync(
                            new DefaultBlockParameterNumber(latestBlockNumber.add(BigInteger.ONE)),
                            fullTransactionObjects,
                            onCompleteFlowable)));
        }
    }

    public Flowable<Transaction> replayPastTransactionsFlowable(
            DefaultBlockParameter startBlock) {
        return replayPastBlocksFlowable(
                startBlock, true, Flowable.empty())
                .flatMapIterable(JsonRpc2_0Rx::toTransactions);
    }

    public Flowable<EthBlock> replayPastAndFutureBlocksFlowable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects,
            long pollingInterval) {

        return replayPastBlocksFlowable(
                startBlock, fullTransactionObjects,
                blockFlowable(fullTransactionObjects, pollingInterval));
    }

    public Flowable<Transaction> replayPastAndFutureTransactionsFlowable(
            DefaultBlockParameter startBlock, long pollingInterval) {
        return replayPastAndFutureBlocksFlowable(
                startBlock, true, pollingInterval)
                .flatMapIterable(JsonRpc2_0Rx::toTransactions);
    }

    private BigInteger getLatestBlockNumber() throws IOException {
        return getBlockNumber(DefaultBlockParameterName.LATEST);
    }

    private BigInteger getBlockNumber(
            DefaultBlockParameter defaultBlockParameter) throws IOException {
        if (defaultBlockParameter instanceof DefaultBlockParameterNumber) {
            return ((DefaultBlockParameterNumber) defaultBlockParameter).getBlockNumber();
        } else {
            EthBlock latestEthBlock = web3j.ethGetBlockByNumber(
                    defaultBlockParameter, false).send();
            return latestEthBlock.getBlock().getNumber();
        }
    }

    private static List<Transaction> toTransactions(EthBlock ethBlock) {
        // If you ever see an exception thrown here, it's probably due to an incomplete chain in
        // Geth/Parity. You should resync to solve.
        return StreamSupport.stream(ethBlock.getBlock().getTransactions())
                .map(transactionResult -> (Transaction) transactionResult.get())
                .collect(Collectors.toList());
    }
}