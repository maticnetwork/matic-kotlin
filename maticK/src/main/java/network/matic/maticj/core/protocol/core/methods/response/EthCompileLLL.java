package network.matic.maticj.core.protocol.core.methods.response;


import network.matic.maticj.core.protocol.core.Response;

/**
 * eth_compileLLL.
 */
public class EthCompileLLL extends Response<String> {
    public String getCompiledSourceCode() {
        return getResult();
    }
}
