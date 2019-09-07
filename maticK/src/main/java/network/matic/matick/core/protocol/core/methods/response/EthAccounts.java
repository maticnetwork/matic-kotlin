package network.matic.matick.core.protocol.core.methods.response;

import java.util.List;

import network.matic.matick.core.protocol.core.Response;

/**
 * eth_accounts.
 */
public class EthAccounts extends Response<List<String>> {
    public List<String> getAccounts() {
        return getResult();
    }
}
