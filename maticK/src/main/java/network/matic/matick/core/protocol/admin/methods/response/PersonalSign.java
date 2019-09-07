package network.matic.matick.core.protocol.admin.methods.response;

import network.matic.matick.core.protocol.core.Response;

/**
 * personal_sign
 * parity_signMessage.
 */
public class PersonalSign extends Response<String> {
    public String getSignedMessage() {
        return getResult();
    }
}
