package network.matic.maticj.core.tx.response;

import network.matic.maticj.core.protocol.Web3j;
import network.matic.maticj.core.protocol.core.methods.response.TransactionReceipt;
import network.matic.maticj.core.protocol.exceptions.TransactionException;

import java.io.IOException;

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
