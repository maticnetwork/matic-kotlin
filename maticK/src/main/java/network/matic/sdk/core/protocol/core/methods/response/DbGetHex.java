package network.matic.sdk.core.protocol.core.methods.response;

import network.matic.sdk.core.protocol.core.Response;

/**
 * db_getHex.
 */
public class DbGetHex extends Response<String> {

    public String getStoredValue() {
        return getResult();
    }
}
