package network.matic.sdk.abi.datatypes.generated;

import java.util.List;

import network.matic.sdk.abi.datatypes.StaticArray;
import network.matic.sdk.abi.datatypes.Type;

/**
 * Auto generated code.
 * <p><strong>Do not modifiy!</strong>
 * <p>Please use network.matic.sdk.core.codegen.AbiTypesGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 */
public class StaticArray19<T extends Type> extends StaticArray<T> {
    @Deprecated
    public StaticArray19(List<T> values) {
        super(19, values);
    }

    @Deprecated
    @SafeVarargs
    public StaticArray19(T... values) {
        super(19, values);
    }

    public StaticArray19(Class<T> type, List<T> values) {
        super(type, 19, values);
    }

    @SafeVarargs
    public StaticArray19(Class<T> type, T... values) {
        super(type, 19, values);
    }
}
