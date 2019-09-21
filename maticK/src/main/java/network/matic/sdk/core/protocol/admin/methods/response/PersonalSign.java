package network.matic.sdk.core.protocol.admin.methods.response;

import network.matic.sdk.core.protocol.core.Response;

/**
 * personal_sign
 * parity_signMessage.
 */
public class PersonalSign extends Response<String> {
    public String getSignedMessage() {
        return getResult();
    }
}
