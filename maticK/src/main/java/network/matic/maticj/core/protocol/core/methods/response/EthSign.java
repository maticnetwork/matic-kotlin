package network.matic.maticj.core.protocol.core.methods.response;

import network.matic.maticj.core.protocol.core.Response;

/**
 * eth_sign.
 */
public class EthSign extends Response<String> {
    public String getSignature() {
        return getResult();
    }
}
