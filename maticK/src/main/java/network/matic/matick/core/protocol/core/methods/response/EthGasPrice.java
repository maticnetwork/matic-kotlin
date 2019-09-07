package network.matic.matick.core.protocol.core.methods.response;

import java.math.BigInteger;

import network.matic.matick.core.protocol.core.Response;
import network.matic.matick.utils.utils.Numeric;

/**
 * eth_gasPrice.
 */
public class EthGasPrice extends Response<String> {
    public BigInteger getGasPrice() {
        return Numeric.decodeQuantity(getResult());
    }
}
