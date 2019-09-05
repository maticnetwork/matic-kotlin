package network.matic.maticj.core.protocol.core.methods.response;

import network.matic.maticj.core.protocol.core.Response;
import network.matic.maticj.utils.utils.Numeric;

import java.math.BigInteger;

/**
 * eth_hashrate.
 */
public class EthHashrate extends Response<String> {
    public BigInteger getHashrate() {
        return Numeric.decodeQuantity(getResult());
    }
}
