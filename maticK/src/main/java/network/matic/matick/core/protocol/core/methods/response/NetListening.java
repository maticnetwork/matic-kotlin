package network.matic.matick.core.protocol.core.methods.response;

import network.matic.matick.core.protocol.core.Response;

/**
 * net_listening.
 */
public class NetListening extends Response<Boolean> {
    public boolean isListening() {
        return getResult();
    }
}
