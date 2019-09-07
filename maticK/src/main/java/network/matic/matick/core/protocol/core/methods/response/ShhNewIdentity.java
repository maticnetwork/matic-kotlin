package network.matic.matick.core.protocol.core.methods.response;

import network.matic.matick.core.protocol.core.Response;

/**
 * shh_newIdentity.
 */
public class ShhNewIdentity extends Response<String> {

    public String getAddress() {
        return getResult();
    }
}
