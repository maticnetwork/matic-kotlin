package network.matic.matick.core.protocol.core.methods.response;


import network.matic.matick.core.protocol.core.Response;

/**
 * eth_submitHashrate.
 */
public class EthSubmitHashrate extends Response<Boolean> {

    public boolean submissionSuccessful() {
        return getResult();
    }
}
