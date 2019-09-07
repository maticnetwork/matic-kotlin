package network.matic.matick.core.protocol.core.methods.response;

import java.math.BigInteger;

import network.matic.matick.core.protocol.core.Response;
import network.matic.matick.utils.utils.Numeric;

/**
 * eth_getBalance.
 */
public class EthGetBalance extends Response<String> {
    public BigInteger getBalance() {
        return Numeric.decodeQuantity(getResult());
    }
}
