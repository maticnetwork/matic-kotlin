package network.matic.matick.core.protocol.core.methods.response;


import network.matic.matick.core.protocol.core.Response;

/**
 * eth_protocolVersion.
 */
public class EthProtocolVersion extends Response<String> {
    public String getProtocolVersion() {
        return getResult();
    }
}
