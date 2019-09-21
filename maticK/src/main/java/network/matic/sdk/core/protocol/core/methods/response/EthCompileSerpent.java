package network.matic.sdk.core.protocol.core.methods.response;


import network.matic.sdk.core.protocol.core.Response;

/**
 * eth_compileSerpent.
 */
public class EthCompileSerpent extends Response<String> {
    public String getCompiledSourceCode() {
        return getResult();
    }
}
