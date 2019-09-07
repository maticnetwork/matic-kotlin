package network.matic.matick.core.protocol.core.methods.response;

import network.matic.matick.core.protocol.core.Response;

/**
 * eth_uninstallFilter.
 */
public class EthUninstallFilter extends Response<Boolean> {
    public boolean isUninstalled() {
        return getResult();
    }
}
