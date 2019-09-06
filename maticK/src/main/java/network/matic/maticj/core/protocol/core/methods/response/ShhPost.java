package network.matic.maticj.core.protocol.core.methods.response;

import network.matic.maticj.core.protocol.core.Response;

/**
 * shh_post.
 */
public class ShhPost extends Response<Boolean> {

    public boolean messageSent() {
        return getResult();
    }
}
