package network.matic.maticj.core.protocol.core.methods.response;


import java.math.BigInteger;

import network.matic.maticj.core.protocol.core.Response;
import network.matic.maticj.utils.utils.Numeric;

/**
 * eth_blockNumber.
 */
public class EthBlockNumber extends Response<String> {
    public BigInteger getBlockNumber() {
        return Numeric.decodeQuantity(getResult());
    }
}
