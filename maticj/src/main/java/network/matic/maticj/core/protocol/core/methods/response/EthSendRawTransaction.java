package network.matic.maticj.core.protocol.core.methods.response;

import network.matic.maticj.core.protocol.core.Response;

/**
 * eth_sendRawTransaction.
 */
public class EthSendRawTransaction extends Response<String> {
    public String getTransactionHash() {
        return getResult();
    }
}
