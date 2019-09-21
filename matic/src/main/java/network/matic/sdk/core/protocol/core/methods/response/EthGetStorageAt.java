package network.matic.sdk.core.protocol.core.methods.response;

import network.matic.sdk.core.protocol.core.Response;

/**
 * eth_getStorageAt.
 */
public class EthGetStorageAt extends Response<String> {
    public String getData() {
        return getResult();
    }
}
