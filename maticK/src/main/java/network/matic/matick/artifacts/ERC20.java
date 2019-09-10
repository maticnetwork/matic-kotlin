package network.matic.matick.artifacts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import network.matic.matick.abi.EventEncoder;
import network.matic.matick.abi.TypeReference;
import network.matic.matick.abi.datatypes.Address;
import network.matic.matick.abi.datatypes.Event;
import network.matic.matick.abi.datatypes.Type;
import network.matic.matick.abi.datatypes.generated.Uint256;
import network.matic.matick.core.protocol.Web3j;
import network.matic.matick.core.protocol.core.DefaultBlockParameter;
import network.matic.matick.core.protocol.core.RemoteCall;
import network.matic.matick.core.protocol.core.methods.request.EthFilter;
import network.matic.matick.core.protocol.core.methods.response.Log;
import network.matic.matick.core.protocol.core.methods.response.TransactionReceipt;
import network.matic.matick.core.tx.Contract;
import network.matic.matick.core.tx.TransactionManager;
import network.matic.matick.core.tx.gas.ContractGasProvider;
import network.matic.matick.crypto.Credentials;
import network.matic.matick.crypto.RawTransaction;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.4.1.
 */
public class ERC20 extends Contract {
    public static final String FUNC_TOTALSUPPLY = "totalSupply";
    public static final String FUNC_BALANCEOF = "balanceOf";
    public static final String FUNC_ALLOWANCE = "allowance";
    public static final String FUNC_TRANSFER = "transfer";
    public static final String FUNC_APPROVE = "approve";
    public static final String FUNC_TRANSFERFROM = "transferFrom";
    public static final String FUNC_INCREASEALLOWANCE = "increaseAllowance";
    public static final String FUNC_DECREASEALLOWANCE = "decreaseAllowance";
    public static final Event TRANSFER_EVENT = new Event("Transfer",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));
    public static final Event APPROVAL_EVENT = new Event("Approval",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));
    ;
    protected static final HashMap<String, String> _addresses;
    ;
    private static final String BINARY = "0x608060405234801561001057600080fd5b50610595806100206000396000f30060806040526004361061008d5763ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663095ea7b3811461009257806318160ddd146100ca57806323b872dd146100f1578063395093511461011b57806370a082311461013f578063a457c2d714610160578063a9059cbb14610184578063dd62ed3e146101a8575b600080fd5b34801561009e57600080fd5b506100b6600160a060020a03600435166024356101cf565b604080519115158252519081900360200190f35b3480156100d657600080fd5b506100df61024d565b60408051918252519081900360200190f35b3480156100fd57600080fd5b506100b6600160a060020a0360043581169060243516604435610253565b34801561012757600080fd5b506100b6600160a060020a03600435166024356102f0565b34801561014b57600080fd5b506100df600160a060020a03600435166103a0565b34801561016c57600080fd5b506100b6600160a060020a03600435166024356103bb565b34801561019057600080fd5b506100b6600160a060020a0360043516602435610406565b3480156101b457600080fd5b506100df600160a060020a036004358116906024351661041c565b6000600160a060020a03831615156101e657600080fd5b336000818152600160209081526040808320600160a060020a03881680855290835292819020869055805186815290519293927f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925929181900390910190a350600192915050565b60025490565b600160a060020a038316600090815260016020908152604080832033845290915281205482111561028357600080fd5b600160a060020a03841660009081526001602090815260408083203384529091529020546102b7908363ffffffff61044716565b600160a060020a03851660009081526001602090815260408083203384529091529020556102e684848461045e565b5060019392505050565b6000600160a060020a038316151561030757600080fd5b336000908152600160209081526040808320600160a060020a038716845290915290205461033b908363ffffffff61055016565b336000818152600160209081526040808320600160a060020a0389168085529083529281902085905580519485525191937f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925929081900390910190a350600192915050565b600160a060020a031660009081526020819052604090205490565b6000600160a060020a03831615156103d257600080fd5b336000908152600160209081526040808320600160a060020a038716845290915290205461033b908363ffffffff61044716565b600061041333848461045e565b50600192915050565b600160a060020a03918216600090815260016020908152604080832093909416825291909152205490565b6000808383111561045757600080fd5b5050900390565b600160a060020a03831660009081526020819052604090205481111561048357600080fd5b600160a060020a038216151561049857600080fd5b600160a060020a0383166000908152602081905260409020546104c1908263ffffffff61044716565b600160a060020a0380851660009081526020819052604080822093909355908416815220546104f6908263ffffffff61055016565b600160a060020a038084166000818152602081815260409182902094909455805185815290519193928716927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef92918290030190a3505050565b60008282018381101561056257600080fd5b93925050505600a165627a7a723058208ca24c2b0761cfd5072794f576e2534f42b34a6794af731eb79a7351c0f922200029";

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected ERC20(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ERC20(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ERC20(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ERC20(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @Deprecated
    public static ERC20 load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ERC20(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ERC20 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ERC20(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ERC20 load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ERC20(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ERC20 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ERC20(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ERC20> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ERC20.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ERC20> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ERC20.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ERC20> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ERC20.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ERC20> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ERC20.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ApprovalEventResponse>() {
            @Override
            public ApprovalEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVAL_EVENT, log);
                ApprovalEventResponse typedResponse = new ApprovalEventResponse();
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventFlowable(filter);
    }

    public RemoteCall<BigInteger> totalSupply() {
        final network.matic.matick.abi.datatypes.Function function = new network.matic.matick.abi.datatypes.Function(FUNC_TOTALSUPPLY,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> balanceOf(String owner) {
        final network.matic.matick.abi.datatypes.Function function = new network.matic.matick.abi.datatypes.Function(FUNC_BALANCEOF,
                Arrays.<Type>asList(new network.matic.matick.abi.datatypes.Address(owner)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> allowance(String owner, String spender) {
        final network.matic.matick.abi.datatypes.Function function = new network.matic.matick.abi.datatypes.Function(FUNC_ALLOWANCE,
                Arrays.<Type>asList(new network.matic.matick.abi.datatypes.Address(owner),
                        new network.matic.matick.abi.datatypes.Address(spender)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public Single<RawTransaction> transfer(String to, BigInteger value) {
        final network.matic.matick.abi.datatypes.Function function = new network.matic.matick.abi.datatypes.Function(
                FUNC_TRANSFER,
                Arrays.<Type>asList(new network.matic.matick.abi.datatypes.Address(to),
                        new network.matic.matick.abi.datatypes.generated.Uint256(value)),
                Collections.<TypeReference<?>>emptyList());
        return createRawTransaction(function);
    }

    public RemoteCall<TransactionReceipt> approve(String spender, BigInteger value) {
        final network.matic.matick.abi.datatypes.Function function = new network.matic.matick.abi.datatypes.Function(
                FUNC_APPROVE,
                Arrays.<Type>asList(new network.matic.matick.abi.datatypes.Address(spender),
                        new network.matic.matick.abi.datatypes.generated.Uint256(value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferFrom(String from, String to, BigInteger value) {
        final network.matic.matick.abi.datatypes.Function function = new network.matic.matick.abi.datatypes.Function(
                FUNC_TRANSFERFROM,
                Arrays.<Type>asList(new network.matic.matick.abi.datatypes.Address(from),
                        new network.matic.matick.abi.datatypes.Address(to),
                        new network.matic.matick.abi.datatypes.generated.Uint256(value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> increaseAllowance(String spender, BigInteger addedValue) {
        final network.matic.matick.abi.datatypes.Function function = new network.matic.matick.abi.datatypes.Function(
                FUNC_INCREASEALLOWANCE,
                Arrays.<Type>asList(new network.matic.matick.abi.datatypes.Address(spender),
                        new network.matic.matick.abi.datatypes.generated.Uint256(addedValue)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> decreaseAllowance(String spender, BigInteger subtractedValue) {
        final network.matic.matick.abi.datatypes.Function function = new network.matic.matick.abi.datatypes.Function(
                FUNC_DECREASEALLOWANCE,
                Arrays.<Type>asList(new network.matic.matick.abi.datatypes.Address(spender),
                        new network.matic.matick.abi.datatypes.generated.Uint256(subtractedValue)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class TransferEventResponse {
        public String from;

        public String to;

        public BigInteger value;
    }

    public static class ApprovalEventResponse {
        public String owner;

        public String spender;

        public BigInteger value;
    }
}
