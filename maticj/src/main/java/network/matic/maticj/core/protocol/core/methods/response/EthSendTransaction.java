package network.matic.maticj.core.protocol.core.methods.response;

import network.matic.maticj.core.protocol.core.Response;

/**
 * eth_sendTransaction.
 */
public class EthSendTransaction extends Response<String> {
    public String getTransactionHash() {
        return getResult();
    }
}
