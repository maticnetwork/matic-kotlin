package network.matic.matick.core.protocol.core.methods.response;

import network.matic.matick.core.protocol.core.Response;

/**
 * eth_coinbase.
 */
public class EthCoinbase extends Response<String> {
    public String getAddress() {
        return getResult();
    }
}
