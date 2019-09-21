package network.matic.sdk.core.protocol.core.methods.response;


import java.math.BigInteger;

import network.matic.sdk.core.protocol.core.Response;
import network.matic.sdk.utils.utils.Numeric;

/**
 * eth_estimateGas.
 */
public class EthEstimateGas extends Response<String> {
    public BigInteger getAmountUsed() {
        return Numeric.decodeQuantity(getResult());
    }
}
