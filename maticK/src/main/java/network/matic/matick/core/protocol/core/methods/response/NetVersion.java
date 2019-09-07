package network.matic.matick.core.protocol.core.methods.response;

import network.matic.matick.core.protocol.core.Response;

/**
 * net_version.
 */
public class NetVersion extends Response<String> {
    public String getNetVersion() {
        return getResult();
    }
}
