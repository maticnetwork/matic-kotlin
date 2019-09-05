package network.matic.maticj.core.protocol.core.methods.response;

import network.matic.maticj.core.protocol.core.Response;

/**
 * shh_version.
 */
public class ShhVersion extends Response<String> {

    public String getVersion() {
        return getResult();
    }
}
