package network.matic.maticj.core.protocol.core.methods.response;

import network.matic.maticj.core.protocol.core.Response;

/**
 * shh_hasIdentity.
 */
public class ShhHasIdentity extends Response<Boolean> {

    public boolean hasPrivateKeyForIdentity() {
        return getResult();
    }
}
