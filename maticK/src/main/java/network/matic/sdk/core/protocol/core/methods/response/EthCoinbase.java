package network.matic.sdk.core.protocol.core.methods.response;

import network.matic.sdk.core.protocol.core.Response;

/**
 * eth_coinbase.
 */
public class EthCoinbase extends Response<String> {
    public String getAddress() {
        return getResult();
    }
}
