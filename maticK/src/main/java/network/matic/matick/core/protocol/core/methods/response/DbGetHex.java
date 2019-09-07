package network.matic.matick.core.protocol.core.methods.response;

import network.matic.matick.core.protocol.core.Response;

/**
 * db_getHex.
 */
public class DbGetHex extends Response<String> {

    public String getStoredValue() {
        return getResult();
    }
}
