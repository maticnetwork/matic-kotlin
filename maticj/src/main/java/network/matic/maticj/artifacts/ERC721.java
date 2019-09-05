package network.matic.maticj.artifacts;

import io.reactivex.Flowable;
import network.matic.maticj.abi.EventEncoder;
import network.matic.maticj.abi.TypeReference;
import network.matic.maticj.abi.datatypes.Address;
import network.matic.maticj.abi.datatypes.Bool;
import network.matic.maticj.abi.datatypes.Event;
import network.matic.maticj.abi.datatypes.Type;
import network.matic.maticj.abi.datatypes.generated.Uint256;
import network.matic.maticj.core.protocol.Web3j;
import network.matic.maticj.core.protocol.core.DefaultBlockParameter;
import network.matic.maticj.core.protocol.core.methods.request.EthFilter;
import network.matic.maticj.core.protocol.core.methods.response.TransactionReceipt;
import network.matic.maticj.core.tx.Contract;
import network.matic.maticj.core.tx.TransactionManager;
import network.matic.maticj.core.tx.gas.ContractGasProvider;
import network.matic.maticj.crypto.Credentials;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import network.matic.maticj.core.protocol.core.RemoteCall;
import network.matic.maticj.core.protocol.core.methods.response.Log;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the network.matic.maticj.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.4.1.
 */
public class ERC721 extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b506100437f01ffc9a70000000000000000000000000000000000000000000000000000000064010000000061007a810204565b6100757f80ac58cd0000000000000000000000000000000000000000000000000000000064010000000061007a810204565b6100e6565b7fffffffff0000000000000000000000000000000000000000000000000000000080821614156100a957600080fd5b7fffffffff00000000000000000000000000000000000000000000000000000000166000908152602081905260409020805460ff19166001179055565b6109a3806100f56000396000f3006080604052600436106100a35763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166301ffc9a781146100a8578063081812fc146100f3578063095ea7b31461012757806323b872dd1461014d57806342842e0e146101775780636352211e146101a157806370a08231146101b9578063a22cb465146101ec578063b88d4fde14610212578063e985e9c514610281575b600080fd5b3480156100b457600080fd5b506100df7bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19600435166102a8565b604080519115158252519081900360200190f35b3480156100ff57600080fd5b5061010b6004356102dc565b60408051600160a060020a039092168252519081900360200190f35b34801561013357600080fd5b5061014b600160a060020a036004351660243561030e565b005b34801561015957600080fd5b5061014b600160a060020a03600435811690602435166044356103c4565b34801561018357600080fd5b5061014b600160a060020a0360043581169060243516604435610452565b3480156101ad57600080fd5b5061010b600435610473565b3480156101c557600080fd5b506101da600160a060020a036004351661049d565b60408051918252519081900360200190f35b3480156101f857600080fd5b5061014b600160a060020a036004351660243515156104d0565b34801561021e57600080fd5b50604080516020601f60643560048181013592830184900484028501840190955281845261014b94600160a060020a0381358116956024803590921695604435953695608494019181908401838280828437509497506105549650505050505050565b34801561028d57600080fd5b506100df600160a060020a036004358116906024351661057c565b7bffffffffffffffffffffffffffffffffffffffffffffffffffffffff191660009081526020819052604090205460ff1690565b60006102e7826105aa565b15156102f257600080fd5b50600090815260026020526040902054600160a060020a031690565b600061031982610473565b9050600160a060020a03838116908216141561033457600080fd5b33600160a060020a03821614806103505750610350813361057c565b151561035b57600080fd5b600082815260026020526040808220805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0387811691821790925591518593918516917f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b92591a4505050565b6103ce33826105c7565b15156103d957600080fd5b600160a060020a03821615156103ee57600080fd5b6103f88382610626565b6104028382610697565b61040c828261072d565b8082600160a060020a031684600160a060020a03167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef60405160405180910390a4505050565b61046e8383836020604051908101604052806000815250610554565b505050565b600081815260016020526040812054600160a060020a031680151561049757600080fd5b92915050565b6000600160a060020a03821615156104b457600080fd5b50600160a060020a031660009081526003602052604090205490565b600160a060020a0382163314156104e657600080fd5b336000818152600460209081526040808320600160a060020a03871680855290835292819020805460ff1916861515908117909155815190815290519293927f17307eab39ab6107e8899845ad3d59bd9653f200f220920489ca2b5937696c31929181900390910190a35050565b61055f8484846103c4565b61056b848484846107bd565b151561057657600080fd5b50505050565b600160a060020a03918216600090815260046020908152604080832093909416825291909152205460ff1690565b600090815260016020526040902054600160a060020a0316151590565b6000806105d383610473565b905080600160a060020a031684600160a060020a0316148061060e575083600160a060020a0316610603846102dc565b600160a060020a0316145b8061061e575061061e818561057c565b949350505050565b81600160a060020a031661063982610473565b600160a060020a03161461064c57600080fd5b600081815260026020526040902054600160a060020a031615610693576000818152600260205260409020805473ffffffffffffffffffffffffffffffffffffffff191690555b5050565b81600160a060020a03166106aa82610473565b600160a060020a0316146106bd57600080fd5b600160a060020a0382166000908152600360205260409020546106e790600163ffffffff61093f16565b600160a060020a03909216600090815260036020908152604080832094909455918152600190915220805473ffffffffffffffffffffffffffffffffffffffff19169055565b600081815260016020526040902054600160a060020a03161561074f57600080fd5b6000818152600160208181526040808420805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a038816908117909155845260039091529091205461079d91610956565b600160a060020a0390921660009081526003602052604090209190915550565b6000806107d285600160a060020a031661096f565b15156107e15760019150610936565b6040517f150b7a020000000000000000000000000000000000000000000000000000000081523360048201818152600160a060020a03898116602485015260448401889052608060648501908152875160848601528751918a169463150b7a0294938c938b938b93909160a490910190602085019080838360005b8381101561087457818101518382015260200161085c565b50505050905090810190601f1680156108a15780820380516001836020036101000a031916815260200191505b5095505050505050602060405180830381600087803b1580156108c357600080fd5b505af11580156108d7573d6000803e3d6000fd5b505050506040513d60208110156108ed57600080fd5b50517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff1981167f150b7a020000000000000000000000000000000000000000000000000000000014925090505b50949350505050565b6000808383111561094f57600080fd5b5050900390565b60008282018381101561096857600080fd5b9392505050565b6000903b11905600a165627a7a72305820040ee799cc5f289a9d349f83c8db8a07847c581b98d789bf8ade1bf5809f1b460029";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_OWNEROF = "ownerOf";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_GETAPPROVED = "getApproved";

    public static final String FUNC_SETAPPROVALFORALL = "setApprovalForAll";

    public static final String FUNC_ISAPPROVEDFORALL = "isApprovedForAll";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final String FUNC_SAFETRANSFERFROM = "safeTransferFrom";

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    public static final Event APPROVAL_EVENT = new Event("Approval", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>(true) {}));
    ;

    public static final Event APPROVALFORALL_EVENT = new Event("ApprovalForAll", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Bool>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected ERC721(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ERC721(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ERC721(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ERC721(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<Boolean> supportsInterface(byte[] interfaceId) {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new network.matic.maticj.abi.datatypes.generated.Bytes4(interfaceId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
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
            typedResponse.approved = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ApprovalEventResponse>() {
            @Override
            public ApprovalEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVAL_EVENT, log);
                ApprovalEventResponse typedResponse = new ApprovalEventResponse();
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.approved = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventFlowable(filter);
    }

    public List<ApprovalForAllEventResponse> getApprovalForAllEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVALFORALL_EVENT, transactionReceipt);
        ArrayList<ApprovalForAllEventResponse> responses = new ArrayList<ApprovalForAllEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ApprovalForAllEventResponse>() {
            @Override
            public ApprovalForAllEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVALFORALL_EVENT, log);
                ApprovalForAllEventResponse typedResponse = new ApprovalForAllEventResponse();
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.operator = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.approved = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ApprovalForAllEventResponse> approvalForAllEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVALFORALL_EVENT));
        return approvalForAllEventFlowable(filter);
    }

    public RemoteCall<BigInteger> balanceOf(String owner) {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new network.matic.maticj.abi.datatypes.Address(owner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> ownerOf(BigInteger tokenId) {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(FUNC_OWNEROF, 
                Arrays.<Type>asList(new network.matic.maticj.abi.datatypes.generated.Uint256(tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> approve(String to, BigInteger tokenId) {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new network.matic.maticj.abi.datatypes.Address(to), 
                new network.matic.maticj.abi.datatypes.generated.Uint256(tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> getApproved(BigInteger tokenId) {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(FUNC_GETAPPROVED, 
                Arrays.<Type>asList(new network.matic.maticj.abi.datatypes.generated.Uint256(tokenId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> setApprovalForAll(String to, Boolean approved) {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(
                FUNC_SETAPPROVALFORALL, 
                Arrays.<Type>asList(new network.matic.maticj.abi.datatypes.Address(to), 
                new network.matic.maticj.abi.datatypes.Bool(approved)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> isApprovedForAll(String owner, String operator) {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(FUNC_ISAPPROVEDFORALL, 
                Arrays.<Type>asList(new network.matic.maticj.abi.datatypes.Address(owner), 
                new network.matic.maticj.abi.datatypes.Address(operator)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> transferFrom(String from, String to, BigInteger tokenId) {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new network.matic.maticj.abi.datatypes.Address(from), 
                new network.matic.maticj.abi.datatypes.Address(to), 
                new network.matic.maticj.abi.datatypes.generated.Uint256(tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger tokenId) {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(
                FUNC_SAFETRANSFERFROM, 
                Arrays.<Type>asList(new network.matic.maticj.abi.datatypes.Address(from), 
                new network.matic.maticj.abi.datatypes.Address(to), 
                new network.matic.maticj.abi.datatypes.generated.Uint256(tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger tokenId, byte[] _data) {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(
                FUNC_SAFETRANSFERFROM, 
                Arrays.<Type>asList(new network.matic.maticj.abi.datatypes.Address(from), 
                new network.matic.maticj.abi.datatypes.Address(to), 
                new network.matic.maticj.abi.datatypes.generated.Uint256(tokenId), 
                new network.matic.maticj.abi.datatypes.DynamicBytes(_data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static ERC721 load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ERC721(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ERC721 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ERC721(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ERC721 load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ERC721(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ERC721 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ERC721(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ERC721> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ERC721.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<ERC721> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ERC721.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ERC721> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ERC721.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ERC721> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ERC721.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class TransferEventResponse{
        public String from;

        public String to;

        public BigInteger tokenId;
    }

    public static class ApprovalEventResponse{
        public String owner;

        public String approved;

        public BigInteger tokenId;
    }

    public static class ApprovalForAllEventResponse{
        public String owner;

        public String operator;

        public Boolean approved;
    }
}
