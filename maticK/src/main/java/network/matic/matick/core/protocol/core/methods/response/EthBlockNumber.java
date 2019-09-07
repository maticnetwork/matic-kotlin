package network.matic.matick.core.protocol.core.methods.response;


import java.math.BigInteger;

import network.matic.matick.core.protocol.core.Response;
import network.matic.matick.utils.utils.Numeric;

/**
 * eth_blockNumber.
 */
public class EthBlockNumber extends Response<String> {
    public BigInteger getBlockNumber() {
        return Numeric.decodeQuantity(getResult());
    }
}
