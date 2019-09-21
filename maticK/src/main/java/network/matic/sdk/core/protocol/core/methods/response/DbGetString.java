package network.matic.sdk.core.protocol.core.methods.response;


import network.matic.sdk.core.protocol.core.Response;

/**
 * db_getString.
 */
public class DbGetString extends Response<String> {

    public String getStoredValue() {
        return getResult();
    }
}
