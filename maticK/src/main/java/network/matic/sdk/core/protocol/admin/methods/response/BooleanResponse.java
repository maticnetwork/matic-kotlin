package network.matic.sdk.core.protocol.admin.methods.response;

import network.matic.sdk.core.protocol.core.Response;

/**
 * Boolean response type.
 */
public class BooleanResponse extends Response<Boolean> {
    public boolean success() {
        return getResult();
    }
}
