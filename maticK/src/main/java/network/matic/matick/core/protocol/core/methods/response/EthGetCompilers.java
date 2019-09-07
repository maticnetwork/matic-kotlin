package network.matic.matick.core.protocol.core.methods.response;


import java.util.List;

import network.matic.matick.core.protocol.core.Response;

/**
 * eth_getCompilers.
 */
public class EthGetCompilers extends Response<List<String>> {
    public List<String> getCompilers() {
        return getResult();
    }
}
