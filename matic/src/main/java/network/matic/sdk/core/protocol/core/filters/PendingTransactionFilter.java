package network.matic.sdk.core.protocol.core.filters;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import network.matic.sdk.core.protocol.Web3j;
import network.matic.sdk.core.protocol.core.Request;
import network.matic.sdk.core.protocol.core.methods.response.EthFilter;
import network.matic.sdk.core.protocol.core.methods.response.EthLog;


/**
 * Handler for working with transaction filter requests.
 */
public class PendingTransactionFilter extends Filter<String> {

    public PendingTransactionFilter(Web3j web3j, Callback<String> callback) {
        super(web3j, callback);
    }

    @Override
    EthFilter sendRequest() throws IOException {
        return web3j.ethNewPendingTransactionFilter().send();
    }

    @Override
    void process(List<EthLog.LogResult> logResults) {
        for (EthLog.LogResult logResult : logResults) {
            if (logResult instanceof EthLog.Hash) {
                String transactionHash = ((EthLog.Hash) logResult).get();
                callback.onEvent(transactionHash);
            } else {
                throw new FilterException(
                        "Unexpected result type: " + logResult.get() + ", required Hash");
            }
        }
    }

    /**
     * Since the pending transaction filter does not support historic filters,
     * the filterId is ignored and an empty optional is returned
     *
     * @param filterId Id of the filter for which the historic log should be retrieved
     * @return Optional.empty()
     */
    @Override
    protected Optional<Request<?, EthLog>> getFilterLogs(BigInteger filterId) {
        return Optional.empty();
    }
}

