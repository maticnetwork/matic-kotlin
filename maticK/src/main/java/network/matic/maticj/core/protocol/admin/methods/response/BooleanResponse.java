package network.matic.maticj.core.protocol.admin.methods.response;

import network.matic.maticj.core.protocol.core.Response;

/**
 * Boolean response type.
 */
public class BooleanResponse extends Response<Boolean> {
    public boolean success() {
        return getResult();
    }
}
