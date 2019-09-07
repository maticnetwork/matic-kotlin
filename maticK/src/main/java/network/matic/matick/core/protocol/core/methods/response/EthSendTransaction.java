package network.matic.matick.core.protocol.core.methods.response;

import network.matic.matick.core.protocol.core.Response;

/**
 * eth_sendTransaction.
 */
public class EthSendTransaction extends Response<String> {
    public String getTransactionHash() {
        return getResult();
    }
}
