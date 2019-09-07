package network.matic.matick.core.protocol.core.methods.response;

import network.matic.matick.core.protocol.core.Response;

/**
 * db_putHex.
 */
public class DbPutHex extends Response<Boolean> {

    public boolean valueStored() {
        return getResult();
    }
}
