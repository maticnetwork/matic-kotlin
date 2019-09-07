package network.matic.matick.core.protocol.core.methods.response;


import network.matic.matick.core.protocol.core.Response;

/**
 * db_getString.
 */
public class DbGetString extends Response<String> {

    public String getStoredValue() {
        return getResult();
    }
}
