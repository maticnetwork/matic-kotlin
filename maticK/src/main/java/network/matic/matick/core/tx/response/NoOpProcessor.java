package network.matic.matick.core.tx.response;

import java.io.IOException;

import network.matic.matick.core.protocol.Web3j;
import network.matic.matick.core.protocol.core.methods.response.TransactionReceipt;
import network.matic.matick.core.protocol.exceptions.TransactionException;

/**
 * Return an {@link EmptyTransactionReceipt} receipt back to callers containing only the
 * transaction hash.
 */
public class NoOpProcessor extends TransactionReceiptProcessor {

    public NoOpProcessor(Web3j web3j) {
        super(web3j);
    }

    @Override
    public TransactionReceipt waitForTransactionReceipt(String transactionHash)
            throws IOException, TransactionException {
        return new EmptyTransactionReceipt(transactionHash);
    }
}
