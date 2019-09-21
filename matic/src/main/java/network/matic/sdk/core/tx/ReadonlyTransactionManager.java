package network.matic.sdk.core.tx;

import java.io.IOException;
import java.math.BigInteger;

import network.matic.sdk.core.protocol.Web3j;
import network.matic.sdk.core.protocol.core.DefaultBlockParameter;
import network.matic.sdk.core.protocol.core.methods.response.EthSendTransaction;

/**
 * Transaction manager implementation for read-only operations on smart contracts.
 */
public class ReadonlyTransactionManager extends TransactionManager {

    public ReadonlyTransactionManager(Web3j web3j, String fromAddress) {
        super(web3j, fromAddress);
    }

    @Override
    public EthSendTransaction sendTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to, String data, BigInteger value)
            throws IOException {
        throw new UnsupportedOperationException(
                "Only read operations are supported by this transaction manager");
    }

    @Override
    public EthSendTransaction sendTransaction(
            BigInteger gasPrice,
            BigInteger gasLimit,
            String to,
            String data,
            BigInteger value,
            boolean constructor)
            throws IOException {
        throw new UnsupportedOperationException(
                "Only read operations are supported by this transaction manager");
    }

    @Override
    public String sendCall(String to, String data, DefaultBlockParameter defaultBlockParameter)
            throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }
}
