package network.matic.maticj.core.protocol.core.filters;

/**
 * Filter callback interface.
 */
public interface Callback<T> {
    void onEvent(T value);
}
