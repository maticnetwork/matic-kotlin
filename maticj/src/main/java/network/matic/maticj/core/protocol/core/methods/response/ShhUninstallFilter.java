package network.matic.maticj.core.protocol.core.methods.response;

import network.matic.maticj.core.protocol.core.Response;

/**
 * shh_uninstallFilter.
 */
public class ShhUninstallFilter extends Response<Boolean> {

    public boolean isUninstalled() {
        return getResult();
    }
}
