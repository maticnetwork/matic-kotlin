package network.matic.matick.core.protocol.core.methods.response;

import network.matic.matick.core.protocol.core.Response;

/**
 * shh_post.
 */
public class ShhPost extends Response<Boolean> {

    public boolean messageSent() {
        return getResult();
    }
}
