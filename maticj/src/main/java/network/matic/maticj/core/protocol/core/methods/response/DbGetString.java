package network.matic.maticj.core.protocol.core.methods.response;


import network.matic.maticj.core.protocol.core.Response;

/**
 * db_getString.
 */
public class DbGetString extends Response<String> {

    public String getStoredValue() {
        return getResult();
    }
}
