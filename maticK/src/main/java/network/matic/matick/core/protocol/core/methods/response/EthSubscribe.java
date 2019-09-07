package network.matic.matick.core.protocol.core.methods.response;

import network.matic.matick.core.protocol.core.Response;

public class EthSubscribe extends Response<String> {
    public String getSubscriptionId() {
        return getResult();
    }
}
