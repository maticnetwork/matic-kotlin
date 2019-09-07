package network.matic.matick.core.tx.response;

import java.io.IOException;

import java8.util.Optional;
import network.matic.matick.core.protocol.Web3j;
import network.matic.matick.core.protocol.core.methods.response.EthGetTransactionReceipt;
import network.matic.matick.core.protocol.core.methods.response.TransactionReceipt;
import network.matic.matick.core.protocol.exceptions.TransactionException;

/**
 * Abstraction for managing how we wait for transaction receipts to be generated on the network.
 */
public abstract class TransactionReceiptProcessor {

    private final Web3j web3j;

    public TransactionReceiptProcessor(Web3j web3j) {
        this.web3j = web3j;
    }

    public abstract TransactionReceipt waitForTransactionReceipt(
            String transactionHash)
            throws IOException, TransactionException;

    Optional<? extends TransactionReceipt> sendTransactionReceiptRequest(
            String transactionHash) throws IOException, TransactionException {
        EthGetTransactionReceipt transactionReceipt =
                web3j.ethGetTransactionReceipt(transactionHash).send();
        if (transactionReceipt.hasError()) {
            throw new TransactionException("Error processing request: "
                    + transactionReceipt.getError().getMessage());
        }

        return transactionReceipt.getTransactionReceipt();
    }
}
