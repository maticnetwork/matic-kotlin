package network.matic.matick.core.protocol.core.methods.response;

import network.matic.matick.core.protocol.core.Response;

/**
 * eth_getStorageAt.
 */
public class EthGetStorageAt extends Response<String> {
    public String getData() {
        return getResult();
    }
}
