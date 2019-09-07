package network.matic.matick.core.protocol.core.methods.response;

import network.matic.matick.core.protocol.core.Response;

/**
 * eth_submitWork.
 */
public class EthSubmitWork extends Response<Boolean> {

    public boolean solutionValid() {
        return getResult();
    }
}
