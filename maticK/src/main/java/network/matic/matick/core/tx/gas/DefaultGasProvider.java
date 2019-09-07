package network.matic.matick.core.tx.gas;

import java.math.BigInteger;

import network.matic.matick.core.tx.Contract;
import network.matic.matick.core.tx.ManagedTransaction;

public class DefaultGasProvider extends StaticGasProvider {
    public static final BigInteger GAS_LIMIT = Contract.GAS_LIMIT;
    public static final BigInteger GAS_PRICE = ManagedTransaction.GAS_PRICE;

    public DefaultGasProvider() {
        super(GAS_PRICE, GAS_LIMIT);
    }
}
