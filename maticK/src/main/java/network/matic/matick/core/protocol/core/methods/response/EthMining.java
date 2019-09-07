package network.matic.matick.core.protocol.core.methods.response;


import network.matic.matick.core.protocol.core.Response;

/**
 * eth_mining.
 */
public class EthMining extends Response<Boolean> {
    public boolean isMining() {
        return getResult();
    }
}
