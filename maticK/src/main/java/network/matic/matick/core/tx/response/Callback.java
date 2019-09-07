package network.matic.matick.core.tx.response;

import network.matic.matick.core.protocol.core.methods.response.TransactionReceipt;

/**
 * Transaction receipt processor callback.
 */
public interface Callback {
    void accept(TransactionReceipt transactionReceipt);

    void exception(Exception exception);
}
