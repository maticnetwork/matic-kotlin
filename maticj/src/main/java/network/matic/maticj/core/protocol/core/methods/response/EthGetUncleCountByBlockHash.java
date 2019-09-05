package network.matic.maticj.core.protocol.core.methods.response;

import network.matic.maticj.core.protocol.core.Response;
import network.matic.maticj.utils.utils.Numeric;

import java.math.BigInteger;

/**
 * eth_getUncleCountByBlockHash.
 */
public class EthGetUncleCountByBlockHash extends Response<String> {
    public BigInteger getUncleCount() {
        return Numeric.decodeQuantity(getResult());
    }
}
