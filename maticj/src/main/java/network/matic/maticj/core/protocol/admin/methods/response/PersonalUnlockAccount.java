package network.matic.maticj.core.protocol.admin.methods.response;

import network.matic.maticj.core.protocol.core.Response;

/**
 * personal_unlockAccount.
 */
public class PersonalUnlockAccount extends Response<Boolean> {
    public Boolean accountUnlocked() {
        return getResult();
    }
}