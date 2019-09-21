package network.matic.sdk.core.protocol.core.methods.response;

import java.util.Collections;
import java.util.List;

import network.matic.sdk.abi.FunctionReturnDecoder;
import network.matic.sdk.abi.TypeReference;
import network.matic.sdk.abi.datatypes.Type;
import network.matic.sdk.abi.datatypes.Utf8String;
import network.matic.sdk.abi.datatypes.generated.AbiTypes;
import network.matic.sdk.core.protocol.core.Response;

/**
 * eth_call.
 */
public class EthCall extends Response<String> {

    // Numeric.toHexString(Hash.sha3("Error(string)".getBytes())).substring(0, 10)
    private static final String errorMethodId = "0x08c379a0";

    @SuppressWarnings("unchecked")
    private static final List<TypeReference<Type>> revertReasonType = Collections.singletonList(
            TypeReference.create((Class<Type>) AbiTypes.getType("string")));

    public String getValue() {
        return getResult();
    }

    public boolean reverts() {
        return getValue() != null && getValue().startsWith(errorMethodId);
    }

    public String getRevertReason() {
        if (reverts()) {
            String hexRevertReason = getValue().substring(errorMethodId.length());
            List<Type> decoded = FunctionReturnDecoder.decode(hexRevertReason, revertReasonType);
            Utf8String decodedRevertReason = (Utf8String) decoded.get(0);
            return decodedRevertReason.getValue();
        }
        return null;
    }
}