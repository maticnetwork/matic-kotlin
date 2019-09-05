package network.matic.maticj.core.protocol.core.methods.response;

import network.matic.maticj.core.protocol.core.Response;
import network.matic.maticj.utils.utils.Numeric;

import java.math.BigInteger;

/**
 * net_peerCount.
 */
public class NetPeerCount extends Response<String> {

    public BigInteger getQuantity() {
        return Numeric.decodeQuantity(getResult());
    }
}
