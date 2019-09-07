package network.matic.matick.core.protocol.core.methods.response;

import network.matic.matick.core.protocol.core.Response;

/**
 * eth_sendRawTransaction.
 */
public class EthSendRawTransaction extends Response<String> {
    public String getTransactionHash() {
        return getResult();
    }
}
