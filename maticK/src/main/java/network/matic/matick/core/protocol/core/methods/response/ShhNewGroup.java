package network.matic.matick.core.protocol.core.methods.response;

import network.matic.matick.core.protocol.core.Response;

/**
 * shh_newGroup.
 */
public class ShhNewGroup extends Response<String> {

    public String getAddress() {
        return getResult();
    }
}
