package network.matic.sdk.core.protocol.core.methods.response;

import java.util.List;

import network.matic.sdk.core.protocol.core.Response;

/**
 * eth_accounts.
 */
public class EthAccounts extends Response<List<String>> {
    public List<String> getAccounts() {
        return getResult();
    }
}
