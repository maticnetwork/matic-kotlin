package network.matic.sdk.core.protocol.core.methods.response;

import network.matic.sdk.core.protocol.core.Response;

/**
 * shh_newGroup.
 */
public class ShhNewGroup extends Response<String> {

    public String getAddress() {
        return getResult();
    }
}
