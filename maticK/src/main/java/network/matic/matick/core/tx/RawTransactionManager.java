package network.matic.matick.core.tx;

import java.io.IOException;
import java.math.BigInteger;

import network.matic.matick.core.protocol.Web3j;
import network.matic.matick.core.protocol.core.DefaultBlockParameter;
import network.matic.matick.core.protocol.core.DefaultBlockParameterName;
import network.matic.matick.core.protocol.core.methods.request.Transaction;
import network.matic.matick.core.protocol.core.methods.response.EthGetTransactionCount;
import network.matic.matick.core.protocol.core.methods.response.EthSendTransaction;
import network.matic.matick.core.tx.exceptions.TxHashMismatchException;
import network.matic.matick.core.tx.response.TransactionReceiptProcessor;
import network.matic.matick.core.utils.TxHashVerifier;
import network.matic.matick.crypto.Credentials;
import network.matic.matick.crypto.RawTransaction;
import network.matic.matick.crypto.TransactionEncoder;
import network.matic.matick.utils.crypto.Hash;
import network.matic.matick.utils.utils.Numeric;

/**
 * TransactionManager implementation using Ethereum wallet file to create and sign transactions
 * locally.
 *
 * <p>This transaction manager provides support for specifying the chain id for transactions as per
 * <a href="https://github.com/ethereum/EIPs/issues/155">EIP155</a>, as well as for locally signing
 * RawTransaction instances without broadcasting them.
 */
public class RawTransactionManager extends TransactionManager {

    private final Web3j web3j;
    final Credentials credentials;

    private final long chainId;

    protected TxHashVerifier txHashVerifier = new TxHashVerifier();

    public RawTransactionManager(Web3j web3j, Credentials credentials, long chainId) {
        super(web3j, credentials.getAddress());

        this.web3j = web3j;
        this.credentials = credentials;

        this.chainId = chainId;
    }

    public RawTransactionManager(
            Web3j web3j, Credentials credentials, long chainId,
            TransactionReceiptProcessor transactionReceiptProcessor) {
        super(transactionReceiptProcessor, credentials.getAddress());

        this.web3j = web3j;
        this.credentials = credentials;

        this.chainId = chainId;
    }

    public RawTransactionManager(
            Web3j web3j, Credentials credentials, long chainId, int attempts, long sleepDuration) {
        super(web3j, attempts, sleepDuration, credentials.getAddress());

        this.web3j = web3j;
        this.credentials = credentials;

        this.chainId = chainId;
    }

    public RawTransactionManager(Web3j web3j, Credentials credentials) {
        this(web3j, credentials, ChainId.NONE);
    }

    public RawTransactionManager(
            Web3j web3j, Credentials credentials, int attempts, int sleepDuration) {
        this(web3j, credentials, ChainId.NONE, attempts, sleepDuration);
    }

    protected BigInteger getNonce() throws IOException {
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                credentials.getAddress(), DefaultBlockParameterName.PENDING).send();

        return ethGetTransactionCount.getTransactionCount();
    }

    public TxHashVerifier getTxHashVerifier() {
        return txHashVerifier;
    }

    public void setTxHashVerifier(TxHashVerifier txHashVerifier) {
        this.txHashVerifier = txHashVerifier;
    }

    @Override
    public EthSendTransaction sendTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, BigInteger value) throws IOException {

        BigInteger nonce = getNonce();

        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasPrice,
                gasLimit,
                to,
                value,
                data);

        return signAndSend(rawTransaction);
    }

    @Override
    public String sendCall(String to, String data, DefaultBlockParameter defaultBlockParameter)
            throws IOException {
        return web3j.ethCall(
                Transaction.createEthCallTransaction(getFromAddress(), to, data),
                defaultBlockParameter)
                .send().getValue();
    }

    /*
     * @param rawTransaction a RawTransaction istance to be signed
     * @return The transaction signed and encoded without ever broadcasting it
     */
    public String sign(RawTransaction rawTransaction) {

        byte[] signedMessage;

        if (chainId > ChainId.NONE) {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
        } else {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        }

        return Numeric.toHexString(signedMessage);
    }

    public EthSendTransaction signAndSend(RawTransaction rawTransaction)
            throws IOException {
        String hexValue = sign(rawTransaction);
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();

        if (ethSendTransaction != null && !ethSendTransaction.hasError()) {
            String txHashLocal = Hash.sha3(hexValue);
            String txHashRemote = ethSendTransaction.getTransactionHash();
            if (!txHashVerifier.verify(txHashLocal, txHashRemote)) {
                throw new TxHashMismatchException(txHashLocal, txHashRemote);
            }
        }

        return ethSendTransaction;
    }
}
