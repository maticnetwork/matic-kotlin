package network.matic.sdk.core.protocol.core.methods.response;

import network.matic.sdk.core.protocol.core.Response;

/**
 * eth_submitWork.
 */
public class EthSubmitWork extends Response<Boolean> {

    public boolean solutionValid() {
        return getResult();
    }
}
