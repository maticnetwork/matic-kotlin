/*
 * Copyright 2019 Web3 Labs LTD.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package network.matic.sdk.core.tx;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.Single;
import java.util.Optional;
import java.util.stream.Collectors;
import network.matic.sdk.abi.EventEncoder;
import network.matic.sdk.abi.EventValues;
import network.matic.sdk.abi.FunctionEncoder;
import network.matic.sdk.abi.FunctionReturnDecoder;
import network.matic.sdk.abi.TypeReference;
import network.matic.sdk.abi.datatypes.Address;
import network.matic.sdk.abi.datatypes.Event;
import network.matic.sdk.abi.datatypes.Function;
import network.matic.sdk.abi.datatypes.Type;
import network.matic.sdk.core.ens.EnsResolver;
import network.matic.sdk.core.protocol.Web3j;
import network.matic.sdk.core.protocol.core.DefaultBlockParameter;
import network.matic.sdk.core.protocol.core.DefaultBlockParameterName;
import network.matic.sdk.core.protocol.core.RemoteCall;
import network.matic.sdk.core.protocol.core.RemoteFunctionCall;
import network.matic.sdk.core.protocol.core.methods.request.Transaction;
import network.matic.sdk.core.protocol.core.methods.response.EthGetCode;
import network.matic.sdk.core.protocol.core.methods.response.Log;
import network.matic.sdk.core.protocol.core.methods.response.TransactionReceipt;
import network.matic.sdk.core.protocol.exceptions.TransactionException;
import network.matic.sdk.core.tx.exceptions.ContractCallException;
import network.matic.sdk.core.tx.gas.ContractGasProvider;
import network.matic.sdk.core.tx.gas.StaticGasProvider;
import network.matic.sdk.crypto.Credentials;
import network.matic.sdk.crypto.RawTransaction;
import network.matic.sdk.utils.utils.Numeric;

/**
 * Solidity contract type abstraction for interacting with smart contracts via native Java types.
 */
@SuppressWarnings("WeakerAccess")
public abstract class Contract extends ManagedTransaction {

    // https://www.reddit.com/r/ethereum/comments/5g8ia6/attention_miners_we_recommend_raising_gas_limit/
    /**
     * @see network.matic.sdk.core.tx.gas.DefaultGasProvider
     * @deprecated ...
     */
    public static final BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);

    public static final String BIN_NOT_PROVIDED = "Bin file was not provided";
    public static final String FUNC_DEPLOY = "deploy";

    protected final String contractBinary;
    protected String contractAddress;
    protected ContractGasProvider gasProvider;
    protected TransactionReceipt transactionReceipt;
    protected Map<String, String> deployedAddresses;
    protected DefaultBlockParameter defaultBlockParameter = DefaultBlockParameterName.LATEST;

    protected Contract(
            String contractBinary,
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider gasProvider) {

        this(
                new EnsResolver(web3j),
                contractBinary,
                contractAddress,
                web3j,
                transactionManager,
                gasProvider);
    }

    protected Contract(
            EnsResolver ensResolver,
            String contractBinary,
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider gasProvider) {

        super(ensResolver, web3j, transactionManager);
        this.contractAddress = resolveContractAddress(contractAddress);
        this.contractBinary = contractBinary;
        this.gasProvider = gasProvider;
    }

    protected Contract(
            String contractBinary,
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider gasProvider) {
        this(
                new EnsResolver(web3j),
                contractBinary,
                contractAddress,
                web3j,
                new RawTransactionManager(web3j, credentials),
                gasProvider);
    }

    @Deprecated
    protected Contract(
            String contractBinary,
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        this(
                new EnsResolver(web3j),
                contractBinary,
                contractAddress,
                web3j,
                transactionManager,
                new StaticGasProvider(gasPrice, gasLimit));
    }

    @Deprecated
    protected Contract(
            String contractBinary,
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        this(
                contractBinary,
                contractAddress,
                web3j,
                new RawTransactionManager(web3j, credentials),
                gasPrice,
                gasLimit);
    }

    @Deprecated
    protected Contract(
            String contractAddress,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        this("", contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    @Deprecated
    protected Contract(
            String contractAddress,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit) {
        this(
                "",
                contractAddress,
                web3j,
                new RawTransactionManager(web3j, credentials),
                gasPrice,
                gasLimit);
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setTransactionReceipt(TransactionReceipt transactionReceipt) {
        this.transactionReceipt = transactionReceipt;
    }

    public String getContractBinary() {
        return contractBinary;
    }

    public void setGasProvider(ContractGasProvider gasProvider) {
        this.gasProvider = gasProvider;
    }

    /**
     * Allow {@code gasPrice} to be set.
     *
     * @param newPrice gas price to use for subsequent transactions
     * @deprecated use ContractGasProvider
     */
    public void setGasPrice(BigInteger newPrice) {
        this.gasProvider = new StaticGasProvider(newPrice, gasProvider.getGasLimit());
    }

    /**
     * Get the current {@code gasPrice} value this contract uses when executing transactions.
     *
     * @return the gas price set on this contract
     * @deprecated use ContractGasProvider
     */
    public BigInteger getGasPrice() {
        return gasProvider.getGasPrice();
    }

    /**
     * Check that the contract deployed at the address associated with this smart contract wrapper
     * is in fact the contract you believe it is.
     *
     * <p>This method uses the <a
     * href="https://github.com/ethereum/wiki/wiki/JSON-RPC#eth_getcode">eth_getCode</a> method to
     * get the contract byte code and validates it against the byte code stored in this smart
     * contract wrapper.
     *
     * @return true if the contract is valid
     * @throws IOException if unable to connect to web3j node
     */
    public boolean isValid() throws IOException {
        if (contractBinary.equals(BIN_NOT_PROVIDED)) {
            throw new UnsupportedOperationException(
                    "Contract binary not present in contract wrapper, "
                            + "please generate your wrapper using -abiFile=<file>");
        }

        if (contractAddress.equals("")) {
            throw new UnsupportedOperationException(
                    "Contract binary not present, you will need to regenerate your smart "
                            + "contract wrapper with web3j v2.2.0+");
        }

        EthGetCode ethGetCode =
                web3j.ethGetCode(contractAddress, DefaultBlockParameterName.LATEST).send();
        if (ethGetCode.hasError()) {
            return false;
        }

        String code = Numeric.cleanHexPrefix(ethGetCode.getCode());
        int metadataIndex = code.indexOf("a165627a7a72305820");
        if (metadataIndex != -1) {
            code = code.substring(0, metadataIndex);
        }
        // There may be multiple contracts in the Solidity bytecode, hence we only check for a
        // match with a subset
        return !code.isEmpty() && contractBinary.contains(code);
    }

    /**
     * If this Contract instance was created at deployment, the TransactionReceipt associated with
     * the initial creation will be provided, e.g. via a <em>deploy</em> method. This will not
     * persist for Contracts instances constructed via a <em>load</em> method.
     *
     * @return the TransactionReceipt generated at contract deployment
     */
    public Optional<TransactionReceipt> getTransactionReceipt() {
        return Optional.ofNullable(transactionReceipt);
    }

    /**
     * Sets the default block parameter. This use useful if one wants to query historical state of a
     * contract.
     *
     * @param defaultBlockParameter the default block parameter
     */
    public void setDefaultBlockParameter(DefaultBlockParameter defaultBlockParameter) {
        this.defaultBlockParameter = defaultBlockParameter;
    }

    /**
     * Execute constant function call - i.e. a call that does not change state of the contract
     *
     * @param function to call
     * @return {@link List} of values returned by function call
     */
    private List<Type> executeCall(Function function) throws IOException {
        String encodedFunction = FunctionEncoder.encode(function);

        String value = call(contractAddress, encodedFunction, defaultBlockParameter);

        return FunctionReturnDecoder.decode(value, function.getOutputParameters());
    }

    @SuppressWarnings("unchecked")
    protected <T extends Type> T executeCallSingleValueReturn(Function function)
            throws IOException {
        List<Type> values = executeCall(function);
        if (!values.isEmpty()) {
            return (T) values.get(0);
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    protected <T extends Type, R> R executeCallSingleValueReturn(
            Function function, Class<R> returnType) throws IOException {
        T result = executeCallSingleValueReturn(function);
        if (result == null) {
            throw new ContractCallException("Empty value (0x) returned from contract");
        }

        Object value = result.getValue();
        if (returnType.isAssignableFrom(value.getClass())) {
            return (R) value;
        } else if (result.getClass().equals(Address.class) && returnType.equals(String.class)) {
            return (R) result.toString(); // cast isn't necessary
        } else {
            throw new ContractCallException(
                    "Unable to convert response: "
                            + value
                            + " to expected type: "
                            + returnType.getSimpleName());
        }
    }

    protected List<Type> executeCallMultipleValueReturn(Function function) throws IOException {
        return executeCall(function);
    }

    protected TransactionReceipt executeTransaction(Function function)
            throws IOException, TransactionException {
        return executeTransaction(function, BigInteger.ZERO);
    }

    private TransactionReceipt executeTransaction(Function function, BigInteger weiValue)
            throws IOException, TransactionException {
        return executeTransaction(FunctionEncoder.encode(function), weiValue, function.getName());
    }

    TransactionReceipt executeTransaction(String data, BigInteger weiValue, String funcName)
            throws TransactionException, IOException {

        return executeTransaction(data, weiValue, funcName, false);
    }

    /**
     * Given the duration required to execute a transaction.
     *
     * @param data     to send in transaction
     * @param weiValue in Wei to send in transaction
     * @return {@link Optional} containing our transaction receipt
     * @throws IOException          if the call to the node fails
     * @throws TransactionException if the transaction was not mined while waiting
     */
    TransactionReceipt executeTransaction(
            String data, BigInteger weiValue, String funcName, boolean constructor)
            throws TransactionException, IOException {
        BigInteger gasLimit = web3j.ethEstimateGas(Transaction.createEthCallTransaction(
                transactionManager.getFromAddress(),
                contractAddress,
                data
                )
        ).send().getAmountUsed();
        TransactionReceipt receipt = send(contractAddress, data, weiValue,
                gasProvider.getGasPrice(funcName),
                gasLimit
        );

        if (!receipt.isStatusOK()) {
            throw new TransactionException(
                    String.format(
                            "Transaction has failed with status: %s. "
                                    + "Gas used: %d. (not-enough gas?)",
                            receipt.getStatus(), receipt.getGasUsed()));
        }

        return receipt;
    }

    /**
     * Create raw transaction to fix eth_sendTransaction not found error
     */
    RawTransaction createRawTransaction(String data, String funcName) throws IOException {
        BigInteger nonce = web3j.ethGetTransactionCount(transactionManager.getFromAddress(),
                DefaultBlockParameterName.PENDING)
                .send().getTransactionCount();
        BigInteger gasLimit = web3j.ethEstimateGas(Transaction.createEthCallTransaction(
                transactionManager.getFromAddress(),
                contractAddress,
                data
                )
        ).send().getAmountUsed();
        BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
        return RawTransaction.createTransaction(nonce, gasPrice,
                gasLimit, contractAddress, data);

    }

    /**
     * Create raw transaction to fix eth_sendTransaction not found error
     */
    RawTransaction createRawTransaction(String data, BigInteger value, String funcName) throws IOException {
        BigInteger nonce = web3j.ethGetTransactionCount(transactionManager.getFromAddress(),
                DefaultBlockParameterName.PENDING)
                .send().getTransactionCount();

        BigInteger gasLimit = web3j.ethEstimateGas(Transaction.createEthCallTransaction(
                transactionManager.getFromAddress(),
                contractAddress,
                data
                )
        ).send().getAmountUsed();

        return RawTransaction.createTransaction(
                nonce,
                gasProvider.getGasPrice(funcName),
                gasLimit,
                contractAddress,
                value,
                data
        );
    }

    protected <T extends Type> RemoteFunctionCall<T> executeRemoteCallSingleValueReturn(
            Function function) {
        return new RemoteFunctionCall<>(function, () -> executeCallSingleValueReturn(function));
    }

    protected <T> RemoteFunctionCall<T> executeRemoteCallSingleValueReturn(
            Function function, Class<T> returnType) {
        return new RemoteFunctionCall<>(
                function, () -> executeCallSingleValueReturn(function, returnType));
    }

    protected RemoteFunctionCall<List<Type>> executeRemoteCallMultipleValueReturn(
            Function function) {
        return new RemoteFunctionCall<>(function, () -> executeCallMultipleValueReturn(function));
    }

    protected RemoteFunctionCall<TransactionReceipt> executeRemoteCallTransaction(
            Function function) {
        return new RemoteFunctionCall<>(function, () -> executeTransaction(function));
    }

    protected RemoteFunctionCall<TransactionReceipt> executeRemoteCallTransaction(
            Function function, BigInteger weiValue) {
        return new RemoteFunctionCall<>(function, () -> executeTransaction(function, weiValue));
    }

    protected Single<RawTransaction> createRawTransaction(Function function) {
        return Single.create(emitter -> {
            RawTransaction transaction = createRawTransaction(
                    FunctionEncoder.encode(function), function.getName());
            emitter.onSuccess(transaction);
        });
    }

    private static <T extends Contract> T create(
            T contract, String binary, String encodedConstructor, BigInteger value)
            throws IOException, TransactionException {
        TransactionReceipt transactionReceipt =
                contract.executeTransaction(binary + encodedConstructor, value, FUNC_DEPLOY, true);

        String contractAddress = transactionReceipt.getContractAddress();
        if (contractAddress == null) {
            throw new RuntimeException("Empty contract address returned");
        }
        contract.setContractAddress(contractAddress);
        contract.setTransactionReceipt(transactionReceipt);

        return contract;
    }

    protected static <T extends Contract> T deploy(
            Class<T> type,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider,
            String binary,
            String encodedConstructor,
            BigInteger value)
            throws RuntimeException, TransactionException {

        try {
            Constructor<T> constructor =
                    type.getDeclaredConstructor(
                            String.class,
                            Web3j.class,
                            Credentials.class,
                            ContractGasProvider.class);
            constructor.setAccessible(true);

            // we want to use null here to ensure that "to" parameter on message is not populated
            T contract = constructor.newInstance(null, web3j, credentials, contractGasProvider);

            return create(contract, binary, encodedConstructor, value);
        } catch (TransactionException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected static <T extends Contract> T deploy(
            Class<T> type,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider,
            String binary,
            String encodedConstructor,
            BigInteger value)
            throws RuntimeException, TransactionException {

        try {
            Constructor<T> constructor =
                    type.getDeclaredConstructor(
                            String.class,
                            Web3j.class,
                            TransactionManager.class,
                            ContractGasProvider.class);
            constructor.setAccessible(true);

            // we want to use null here to ensure that "to" parameter on message is not populated
            T contract =
                    constructor.newInstance(null, web3j, transactionManager, contractGasProvider);
            return create(contract, binary, encodedConstructor, value);
        } catch (TransactionException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    protected static <T extends Contract> T deploy(
            Class<T> type,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String binary,
            String encodedConstructor,
            BigInteger value)
            throws RuntimeException, TransactionException {

        return deploy(
                type,
                web3j,
                credentials,
                new StaticGasProvider(gasPrice, gasLimit),
                binary,
                encodedConstructor,
                value);
    }

    @Deprecated
    protected static <T extends Contract> T deploy(
            Class<T> type,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String binary,
            String encodedConstructor,
            BigInteger value)
            throws RuntimeException, TransactionException {

        return deploy(
                type,
                web3j,
                transactionManager,
                new StaticGasProvider(gasPrice, gasLimit),
                binary,
                encodedConstructor,
                value);
    }

    public static <T extends Contract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String binary,
            String encodedConstructor,
            BigInteger value) {
        return new RemoteCall<>(
                () ->
                        deploy(
                                type,
                                web3j,
                                credentials,
                                gasPrice,
                                gasLimit,
                                binary,
                                encodedConstructor,
                                value));
    }

    public static <T extends Contract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            Web3j web3j,
            Credentials credentials,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String binary,
            String encodedConstructor) {
        return deployRemoteCall(
                type,
                web3j,
                credentials,
                gasPrice,
                gasLimit,
                binary,
                encodedConstructor,
                BigInteger.ZERO);
    }

    public static <T extends Contract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider,
            String binary,
            String encodedConstructor,
            BigInteger value) {
        return new RemoteCall<>(
                () ->
                        deploy(
                                type,
                                web3j,
                                credentials,
                                contractGasProvider,
                                binary,
                                encodedConstructor,
                                value));
    }

    public static <T extends Contract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider contractGasProvider,
            String binary,
            String encodedConstructor) {
        return new RemoteCall<>(
                () ->
                        deploy(
                                type,
                                web3j,
                                credentials,
                                contractGasProvider,
                                binary,
                                encodedConstructor,
                                BigInteger.ZERO));
    }

    public static <T extends Contract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String binary,
            String encodedConstructor,
            BigInteger value) {
        return new RemoteCall<>(
                () -> deploy(
                        type,
                        web3j,
                        transactionManager,
                        gasPrice,
                        gasLimit,
                        binary,
                        encodedConstructor,
                        value));
    }

    public static <T extends Contract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            Web3j web3j,
            TransactionManager transactionManager,
            BigInteger gasPrice,
            BigInteger gasLimit,
            String binary,
            String encodedConstructor) {
        return deployRemoteCall(
                type,
                web3j,
                transactionManager,
                gasPrice,
                gasLimit,
                binary,
                encodedConstructor,
                BigInteger.ZERO);
    }

    public static <T extends Contract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider,
            String binary,
            String encodedConstructor,
            BigInteger value) {
        return new RemoteCall<>(
                () ->
                        deploy(
                                type,
                                web3j,
                                transactionManager,
                                contractGasProvider,
                                binary,
                                encodedConstructor,
                                value));
    }

    public static <T extends Contract> RemoteCall<T> deployRemoteCall(
            Class<T> type,
            Web3j web3j,
            TransactionManager transactionManager,
            ContractGasProvider contractGasProvider,
            String binary,
            String encodedConstructor) {
        return new RemoteCall<>(
                () ->
                        deploy(
                                type,
                                web3j,
                                transactionManager,
                                contractGasProvider,
                                binary,
                                encodedConstructor,
                                BigInteger.ZERO));
    }

    public static EventValues staticExtractEventParameters(Event event, Log log) {
        final List<String> topics = log.getTopics();
        String encodedEventSignature = EventEncoder.encode(event);
        if (topics == null || topics.size() == 0 || !topics.get(0).equals(encodedEventSignature)) {
            return null;
        }

        List<Type> indexedValues = new ArrayList<>();
        List<Type> nonIndexedValues =
                FunctionReturnDecoder.decode(log.getData(), event.getNonIndexedParameters());

        List<TypeReference<Type>> indexedParameters = event.getIndexedParameters();
        for (int i = 0; i < indexedParameters.size(); i++) {
            Type value =
                    FunctionReturnDecoder.decodeIndexedValue(
                            topics.get(i + 1), indexedParameters.get(i));
            indexedValues.add(value);
        }
        return new EventValues(indexedValues, nonIndexedValues);
    }

    protected String resolveContractAddress(String contractAddress) {
        return ensResolver.resolve(contractAddress);
    }

    protected EventValues extractEventParameters(Event event, Log log) {
        return staticExtractEventParameters(event, log);
    }

    protected List<EventValues> extractEventParameters(
            Event event, TransactionReceipt transactionReceipt) {
        return transactionReceipt.getLogs().stream()
                .map(log -> extractEventParameters(event, log))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    protected EventValuesWithLog extractEventParametersWithLog(Event event, Log log) {
        return staticExtractEventParametersWithLog(event, log);
    }

    protected static EventValuesWithLog staticExtractEventParametersWithLog(Event event, Log log) {
        final EventValues eventValues = staticExtractEventParameters(event, log);
        return (eventValues == null) ? null : new EventValuesWithLog(eventValues, log);
    }

    protected List<EventValuesWithLog> extractEventParametersWithLog(
            Event event, TransactionReceipt transactionReceipt) {
        return transactionReceipt.getLogs().stream()
                .map(log -> extractEventParametersWithLog(event, log))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Subclasses should implement this method to return pre-existing addresses for deployed
     * contracts.
     *
     * @param networkId the network id, for example "1" for the main-net, "3" for ropsten, etc.
     * @return the deployed address of the contract, if known, and null otherwise.
     */
    protected String getStaticDeployedAddress(String networkId) {
        return null;
    }

    public final void setDeployedAddress(String networkId, String address) {
        if (deployedAddresses == null) {
            deployedAddresses = new HashMap<>();
        }
        deployedAddresses.put(networkId, address);
    }

    public final String getDeployedAddress(String networkId) {
        String addr = null;
        if (deployedAddresses != null) {
            addr = deployedAddresses.get(networkId);
        }
        return addr == null ? getStaticDeployedAddress(networkId) : addr;
    }

    /**
     * Adds a log field to {@link EventValues}.
     */
    public static class EventValuesWithLog {
        private final EventValues eventValues;
        private final Log log;

        private EventValuesWithLog(EventValues eventValues, Log log) {
            this.eventValues = eventValues;
            this.log = log;
        }

        public List<Type> getIndexedValues() {
            return eventValues.getIndexedValues();
        }

        public List<Type> getNonIndexedValues() {
            return eventValues.getNonIndexedValues();
        }

        public Log getLog() {
            return log;
        }
    }

    @SuppressWarnings("unchecked")
    protected static <S extends Type, T> List<T> convertToNative(List<S> arr) {
        List<T> out = new ArrayList<>();
        for (final S s : arr) {
            out.add((T) s.getValue());
        }
        return out;
    }
}