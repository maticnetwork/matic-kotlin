package network.matic.sdk.core.protocol.core.methods.response;

import java.math.BigInteger;

import network.matic.sdk.core.protocol.core.Response;
import network.matic.sdk.utils.utils.Numeric;

/**
 * eth_getUncleCountByBlockNumber.
 */
public class EthGetUncleCountByBlockNumber extends Response<String> {
    public BigInteger getUncleCount() {
        return Numeric.decodeQuantity(getResult());
    }
}
