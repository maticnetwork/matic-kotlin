package network.matic.matick.core.protocol.core.filters;

/**
 * Filter callback interface.
 */
public interface Callback<T> {
    void onEvent(T value);
}
