package network.matic.matick.core.protocol.core.methods.response;

import java.math.BigInteger;

import network.matic.matick.core.protocol.core.Response;
import network.matic.matick.utils.utils.Numeric;

/**
 * shh_newFilter.
 */
public class ShhNewFilter extends Response<String> {

    public BigInteger getFilterId() {
        return Numeric.decodeQuantity(getResult());
    }
}
