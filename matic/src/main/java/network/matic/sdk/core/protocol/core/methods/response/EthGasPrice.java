package network.matic.sdk.core.protocol.core.methods.response;

import java.math.BigInteger;

import network.matic.sdk.core.protocol.core.Response;
import network.matic.sdk.utils.utils.Numeric;

/**
 * eth_gasPrice.
 */
public class EthGasPrice extends Response<String> {
    public BigInteger getGasPrice() {
        return Numeric.decodeQuantity(getResult());
    }
}
