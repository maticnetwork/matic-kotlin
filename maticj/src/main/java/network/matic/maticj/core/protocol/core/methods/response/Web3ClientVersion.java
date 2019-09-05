package network.matic.maticj.core.protocol.core.methods.response;

import network.matic.maticj.core.protocol.core.Response;

/**
 * web3_clientVersion.
 */
public class Web3ClientVersion extends Response<String> {

    public String getWeb3ClientVersion() {
        return getResult();
    }
}
