package network.matic.sdk.core.tx.response;

import java.io.IOException;

import java8.util.Optional;
import network.matic.sdk.core.protocol.Web3j;
import network.matic.sdk.core.protocol.core.methods.response.TransactionReceipt;
import network.matic.sdk.core.protocol.exceptions.TransactionException;

/**
 * With each provided transaction hash, poll until we obtain a transaction receipt.
 */
public class PollingTransactionReceiptProcessor extends TransactionReceiptProcessor {

    protected final long sleepDuration;
    protected final int attempts;

    public PollingTransactionReceiptProcessor(Web3j web3j, long sleepDuration, int attempts) {
        super(web3j);
        this.sleepDuration = sleepDuration;
        this.attempts = attempts;
    }

    @Override
    public TransactionReceipt waitForTransactionReceipt(
            String transactionHash)
            throws IOException, TransactionException {

        return getTransactionReceipt(transactionHash, sleepDuration, attempts);
    }

    private TransactionReceipt getTransactionReceipt(
            String transactionHash, long sleepDuration, int attempts)
            throws IOException, TransactionException {

        Optional<TransactionReceipt> receiptOptional =
                (Optional<TransactionReceipt>) sendTransactionReceiptRequest(transactionHash);
        for (int i = 0; i < attempts; i++) {
            if (!receiptOptional.isPresent()) {
                try {
                    Thread.sleep(sleepDuration);
                } catch (InterruptedException e) {
                    throw new TransactionException(e);
                }
                receiptOptional =
                        (Optional<TransactionReceipt>)
                                sendTransactionReceiptRequest(transactionHash);
            } else {
                return receiptOptional.get();
            }
        }

        throw new TransactionException("Transaction receipt was not generated after "
                + ((sleepDuration * attempts) / 1000
                + " seconds for transaction: " + transactionHash), transactionHash);
    }
}
