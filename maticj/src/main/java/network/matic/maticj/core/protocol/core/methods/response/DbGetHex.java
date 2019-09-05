package network.matic.maticj.core.protocol.core.methods.response;

import network.matic.maticj.core.protocol.core.Response;

/**
 * db_getHex.
 */
public class DbGetHex extends Response<String> {

    public String getStoredValue() {
        return getResult();
    }
}
