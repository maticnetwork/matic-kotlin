package network.matic.maticj.core.protocol.core.methods.response;


import network.matic.maticj.core.protocol.core.Response;

/**
 * eth_getCode.
 */
public class EthGetCode extends Response<String> {
    public String getCode() {
        return getResult();
    }
}
