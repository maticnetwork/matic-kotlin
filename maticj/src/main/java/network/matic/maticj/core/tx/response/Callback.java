package network.matic.maticj.core.tx.response;

import network.matic.maticj.core.protocol.core.methods.response.TransactionReceipt;

/**
 * Transaction receipt processor callback.
 */
public interface Callback {
    void accept(TransactionReceipt transactionReceipt);

    void exception(Exception exception);
}
