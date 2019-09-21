package network.matic.sdk.core.protocol.core.methods.response;

import network.matic.sdk.core.protocol.core.Response;

public class EthSubscribe extends Response<String> {
    public String getSubscriptionId() {
        return getResult();
    }
}
