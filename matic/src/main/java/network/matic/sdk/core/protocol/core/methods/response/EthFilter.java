package network.matic.sdk.core.protocol.core.methods.response;


import java.math.BigInteger;

import network.matic.sdk.core.protocol.core.Response;
import network.matic.sdk.utils.utils.Numeric;

/**
 * eth_newFilter.
 */
public class EthFilter extends Response<String> {
    public BigInteger getFilterId() {
        return Numeric.decodeQuantity(getResult());
    }
}
