package network.matic.matick.core.protocol.core;

import java.util.List;
import java.util.concurrent.Callable;

import network.matic.matick.abi.FunctionEncoder;
import network.matic.matick.abi.FunctionReturnDecoder;
import network.matic.matick.abi.datatypes.Function;
import network.matic.matick.abi.datatypes.Type;

public class RemoteFunctionCall<T> extends RemoteCall<T> {

    private final Function function;

    public RemoteFunctionCall(Function function, Callable<T> callable) {
        super(callable);
        this.function = function;
    }

    /**
     * return an encoded function, so it can be manually signed and transmitted
     *
     * @return the function call, encoded.
     */
    public String encodeFunctionCall() {
        return FunctionEncoder.encode(function);
    }

    /**
     * decode a method response
     *
     * @param response
     * @return
     */
    public List<Type> decodeFunctionResponse(String response) {
        return FunctionReturnDecoder.decode(response, function.getOutputParameters());
    }
}
