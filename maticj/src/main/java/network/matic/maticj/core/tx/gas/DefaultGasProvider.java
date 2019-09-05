package network.matic.maticj.core.tx.gas;

import network.matic.maticj.core.tx.Contract;
import network.matic.maticj.core.tx.ManagedTransaction;

import java.math.BigInteger;

public class DefaultGasProvider extends StaticGasProvider {
    public static final BigInteger GAS_LIMIT = Contract.GAS_LIMIT;
    public static final BigInteger GAS_PRICE = ManagedTransaction.GAS_PRICE;

    public DefaultGasProvider() {
        super(GAS_PRICE, GAS_LIMIT);
    }
}
