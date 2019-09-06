package network.matic.maticj.core.protocol.core.methods.response;


import java.math.BigInteger;

import network.matic.maticj.core.protocol.core.Response;
import network.matic.maticj.utils.utils.Numeric;

/**
 * eth_newFilter.
 */
public class EthFilter extends Response<String> {
    public BigInteger getFilterId() {
        return Numeric.decodeQuantity(getResult());
    }
}
