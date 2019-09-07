package network.matic.matick.core.protocol.core.methods.response;

import java.math.BigInteger;

import network.matic.matick.core.protocol.core.Response;
import network.matic.matick.utils.utils.Numeric;

/**
 * eth_getUncleCountByBlockHash.
 */
public class EthGetUncleCountByBlockHash extends Response<String> {
    public BigInteger getUncleCount() {
        return Numeric.decodeQuantity(getResult());
    }
}
