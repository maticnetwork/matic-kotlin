package network.matic.matick.core.protocol.core.methods.response;


import network.matic.matick.core.protocol.core.Response;

/**
 * eth_compileLLL.
 */
public class EthCompileLLL extends Response<String> {
    public String getCompiledSourceCode() {
        return getResult();
    }
}
