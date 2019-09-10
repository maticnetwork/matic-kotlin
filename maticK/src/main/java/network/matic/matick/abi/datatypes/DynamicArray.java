package network.matic.matick.abi.datatypes;

import java.util.List;

import network.matic.matick.abi.datatypes.generated.AbiTypes;

/**
 * Dynamic array type.
 */
public class DynamicArray<T extends Type> extends Array<T> {

    @Deprecated
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public DynamicArray(T... values) {
        super((Class<T>) AbiTypes.getType(values[0].getTypeAsString()), values);
    }

    @Deprecated
    @SuppressWarnings("unchecked")
    public DynamicArray(List<T> values) {
        super((Class<T>) AbiTypes.getType(values.get(0).getTypeAsString()), values);
    }

    @Deprecated
    @SuppressWarnings("unchecked")
    private DynamicArray(String type) {
        super((Class<T>) AbiTypes.getType(type));
    }

    public DynamicArray(Class<T> type, List<T> values) {
        super(type, values);
    }

    @SafeVarargs
    public DynamicArray(Class<T> type, T... values) {
        super(type, values);
    }

    @Deprecated
    public static DynamicArray empty(String type) {
        return new DynamicArray(type);
    }

    @Override
    public String getTypeAsString() {
        return AbiTypes.getTypeAString(getComponentType()) + "[]";
    }
}
