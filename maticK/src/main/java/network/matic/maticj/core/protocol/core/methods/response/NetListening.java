package network.matic.maticj.core.protocol.core.methods.response;

import network.matic.maticj.core.protocol.core.Response;

/**
 * net_listening.
 */
public class NetListening extends Response<Boolean> {
    public boolean isListening() {
        return getResult();
    }
}
