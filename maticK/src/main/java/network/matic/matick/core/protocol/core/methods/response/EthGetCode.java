package network.matic.matick.core.protocol.core.methods.response;


import network.matic.matick.core.protocol.core.Response;

/**
 * eth_getCode.
 */
public class EthGetCode extends Response<String> {
    public String getCode() {
        return getResult();
    }
}
