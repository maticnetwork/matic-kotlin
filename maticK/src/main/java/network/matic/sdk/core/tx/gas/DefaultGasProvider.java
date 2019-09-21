package network.matic.sdk.core.tx.gas;

import java.math.BigInteger;

import network.matic.sdk.core.tx.Contract;
import network.matic.sdk.core.tx.ManagedTransaction;

public class DefaultGasProvider extends StaticGasProvider {
    public static final BigInteger GAS_LIMIT = Contract.GAS_LIMIT;
    public static final BigInteger GAS_PRICE = ManagedTransaction.GAS_PRICE;

    public DefaultGasProvider() {
        super(GAS_PRICE, GAS_LIMIT);
    }
}
