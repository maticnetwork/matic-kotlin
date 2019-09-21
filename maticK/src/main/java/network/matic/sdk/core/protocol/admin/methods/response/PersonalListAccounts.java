package network.matic.sdk.core.protocol.admin.methods.response;

import java.util.List;

import network.matic.sdk.core.protocol.core.Response;

/**
 * personal_listAccounts.
 */
public class PersonalListAccounts extends Response<List<String>> {
    public List<String> getAccountIds() {
        return getResult();
    }
}
