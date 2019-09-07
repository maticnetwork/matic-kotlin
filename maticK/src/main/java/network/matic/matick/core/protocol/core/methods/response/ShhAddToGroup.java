package network.matic.matick.core.protocol.core.methods.response;

import network.matic.matick.core.protocol.core.Response;

/**
 * shh_addToGroup.
 */
public class ShhAddToGroup extends Response<Boolean> {

    public boolean addedToGroup() {
        return getResult();
    }
}
