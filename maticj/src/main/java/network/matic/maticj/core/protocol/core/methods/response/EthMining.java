package network.matic.maticj.core.protocol.core.methods.response;


import network.matic.maticj.core.protocol.core.Response;

/**
 * eth_mining.
 */
public class EthMining extends Response<Boolean> {
    public boolean isMining() {
        return getResult();
    }
}
