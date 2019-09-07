/*
 * Copyright 2019 Web3 Labs LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package network.matic.matick.core.protocol.core.filters;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import network.matic.matick.core.protocol.Web3j;
import network.matic.matick.core.protocol.core.Request;
import network.matic.matick.core.protocol.core.methods.response.EthFilter;
import network.matic.matick.core.protocol.core.methods.response.EthLog;
import network.matic.matick.core.protocol.core.methods.response.Log;


/** Log filter handler. */
public class LogFilter extends Filter<Log> {

    private  final network.matic.matick.core.protocol.core.methods.request.EthFilter ethFilter;

    public LogFilter(
            Web3j web3j,
            Callback<Log> callback,
            network.matic.matick.core.protocol.core.methods.request.EthFilter ethFilter) {
        super(web3j, callback);
        this.ethFilter = ethFilter;
    }

    @Override
    EthFilter sendRequest() throws IOException {
        return web3j.ethNewFilter(ethFilter).send();
    }

    @Override
    void process(List<EthLog.LogResult> logResults) {
        for (EthLog.LogResult logResult : logResults) {
            if (logResult instanceof EthLog.LogObject) {
                Log log = ((EthLog.LogObject) logResult).get();
                callback.onEvent(log);
            } else {
                throw new FilterException(
                        "Unexpected result type: " + logResult.get() + " required LogObject");
            }
        }
    }

    @Override
    protected Optional<Request<?, EthLog>> getFilterLogs(BigInteger filterId) {
        return Optional.of(web3j.ethGetFilterLogs(filterId));
    }
}