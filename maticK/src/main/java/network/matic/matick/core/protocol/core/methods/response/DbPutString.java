package network.matic.matick.core.protocol.core.methods.response;

import network.matic.matick.core.protocol.core.Response;

/**
 * db_putString.
 */
public class DbPutString extends Response<Boolean> {

    public boolean valueStored() {
        return getResult();
    }
}
