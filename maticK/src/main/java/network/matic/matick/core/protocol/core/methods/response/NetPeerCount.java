package network.matic.matick.core.protocol.core.methods.response;

import java.math.BigInteger;

import network.matic.matick.core.protocol.core.Response;
import network.matic.matick.utils.utils.Numeric;

/**
 * net_peerCount.
 */
public class NetPeerCount extends Response<String> {

    public BigInteger getQuantity() {
        return Numeric.decodeQuantity(getResult());
    }
}
