package network.matic.sdk.core.protocol.core.methods.response;

import network.matic.sdk.core.protocol.core.Response;

/**
 * net_version.
 */
public class NetVersion extends Response<String> {
    public String getNetVersion() {
        return getResult();
    }
}
