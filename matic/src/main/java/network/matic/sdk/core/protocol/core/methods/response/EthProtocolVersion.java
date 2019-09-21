package network.matic.sdk.core.protocol.core.methods.response;


import network.matic.sdk.core.protocol.core.Response;

/**
 * eth_protocolVersion.
 */
public class EthProtocolVersion extends Response<String> {
    public String getProtocolVersion() {
        return getResult();
    }
}
