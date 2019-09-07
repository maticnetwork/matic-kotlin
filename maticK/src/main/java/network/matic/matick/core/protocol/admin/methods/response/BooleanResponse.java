package network.matic.matick.core.protocol.admin.methods.response;

import network.matic.matick.core.protocol.core.Response;

/**
 * Boolean response type.
 */
public class BooleanResponse extends Response<Boolean> {
    public boolean success() {
        return getResult();
    }
}
