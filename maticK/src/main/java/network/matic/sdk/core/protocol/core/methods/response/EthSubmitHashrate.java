package network.matic.sdk.core.protocol.core.methods.response;


import network.matic.sdk.core.protocol.core.Response;

/**
 * eth_submitHashrate.
 */
public class EthSubmitHashrate extends Response<Boolean> {

    public boolean submissionSuccessful() {
        return getResult();
    }
}
