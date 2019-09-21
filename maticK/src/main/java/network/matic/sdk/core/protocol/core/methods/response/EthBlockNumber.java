package network.matic.sdk.core.protocol.core.methods.response;


import java.math.BigInteger;

import network.matic.sdk.core.protocol.core.Response;
import network.matic.sdk.utils.utils.Numeric;

/**
 * eth_blockNumber.
 */
public class EthBlockNumber extends Response<String> {
    public BigInteger getBlockNumber() {
        return Numeric.decodeQuantity(getResult());
    }
}
