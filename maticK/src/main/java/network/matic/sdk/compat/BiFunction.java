package network.matic.sdk.compat;

/**
 * Ports {@link java.util.function.BiFunction}.
 */
public interface BiFunction<T, U, R> {

    R apply(T t, U u);
}
