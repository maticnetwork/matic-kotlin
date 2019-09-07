package network.matic.matick.core.protocol.core.methods.response;

import network.matic.matick.core.protocol.core.Response;

/**
 * eth_sign.
 */
public class EthSign extends Response<String> {
    public String getSignature() {
        return getResult();
    }
}
