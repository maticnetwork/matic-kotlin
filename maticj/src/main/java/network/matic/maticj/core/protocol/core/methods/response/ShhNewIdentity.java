package network.matic.maticj.core.protocol.core.methods.response;

import network.matic.maticj.core.protocol.core.Response;

/**
 * shh_newIdentity.
 */
public class ShhNewIdentity extends Response<String> {

    public String getAddress() {
        return getResult();
    }
}
