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
public class StaticArray29<T extends Type> extends StaticArray<T> {
    @Deprecated
    public StaticArray29(List<T> values) {
        super(29, values);
    }

    @Deprecated
    @SafeVarargs
    public StaticArray29(T... values) {
        super(29, values);
    }

    public StaticArray29(Class<T> type, List<T> values) {
        super(type, 29, values);
    }

    @SafeVarargs
    public StaticArray29(Class<T> type, T... values) {
        super(type, 29, values);
    }
}
