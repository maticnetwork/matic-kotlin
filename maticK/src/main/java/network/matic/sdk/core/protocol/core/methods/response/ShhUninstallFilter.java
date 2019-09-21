package network.matic.sdk.core.protocol.core.methods.response;

import network.matic.sdk.core.protocol.core.Response;

/**
 * shh_uninstallFilter.
 */
public class ShhUninstallFilter extends Response<Boolean> {

    public boolean isUninstalled() {
        return getResult();
    }
}
