package network.matic.maticj.artifacts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import network.matic.maticj.abi.EventEncoder;
import network.matic.maticj.abi.TypeReference;
import network.matic.maticj.abi.datatypes.Address;
import network.matic.maticj.abi.datatypes.Bool;
import network.matic.maticj.abi.datatypes.DynamicBytes;
import network.matic.maticj.abi.datatypes.Event;
import network.matic.maticj.abi.datatypes.Type;
import network.matic.maticj.abi.datatypes.generated.Bytes1;
import network.matic.maticj.abi.datatypes.generated.Bytes32;
import network.matic.maticj.abi.datatypes.generated.Uint256;
import network.matic.maticj.core.protocol.Web3j;
import network.matic.maticj.core.protocol.core.DefaultBlockParameter;
import network.matic.maticj.core.protocol.core.methods.request.EthFilter;
import network.matic.maticj.core.protocol.core.methods.response.TransactionReceipt;
import network.matic.maticj.core.tx.Contract;
import network.matic.maticj.core.tx.TransactionManager;
import network.matic.maticj.core.tx.gas.ContractGasProvider;
import network.matic.maticj.crypto.Credentials;


import network.matic.maticj.core.protocol.core.RemoteCall;
import network.matic.maticj.core.protocol.core.methods.response.Log;
import network.matic.maticj.tuples.generated.Tuple5;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the network.matic.maticj.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.4.1.
 */
public class DepositManager extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b5060048054600160a060020a031916331790819055604051600160a060020a0391909116906000907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908290a36001600755610ce7806100716000396000f30060806040526004361061013d5763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416630472bc3c81146101425780632c2d1a3b1461016c5780632dfdf0b514610181578063325906541461019657806340828ebf146101e557806346e11a8d14610222578063494f2a78146102455780634b57b0be14610272578063715018a6146102875780637d1a3d371461029c5780638da5cb5b146102e65780638f32d59b146102fb5780639025e64c14610324578063987ab9db146103ae578063a831fa07146103c3578063b02c43d0146103d8578063b45d1f68146103f0578063c763e5a114610411578063daa09e5414610426578063e117694b14610447578063e486033914610473578063e8afa8e814610494578063f2fde38b146104b5578063fb0df30f146104d6575b600080fd5b34801561014e57600080fd5b5061015a6004356104ee565b60408051918252519081900360200190f35b34801561017857600080fd5b5061015a61051c565b34801561018d57600080fd5b5061015a610551565b3480156101a257600080fd5b506101ae600435610557565b60408051958652600160a060020a039485166020870152929093168483015260608401526080830191909152519081900360a00190f35b3480156101f157600080fd5b50610206600160a060020a03600435166105d0565b60408051600160a060020a039092168252519081900360200190f35b34801561022e57600080fd5b50610243600160a060020a03600435166105eb565b005b34801561025157600080fd5b50610243600435600160a060020a0360243581169060443516606435610605565b34801561027e57600080fd5b50610206610821565b34801561029357600080fd5b50610243610830565b3480156102a857600080fd5b506102b161089a565b604080517fff000000000000000000000000000000000000000000000000000000000000009092168252519081900360200190f35b3480156102f257600080fd5b506102066108be565b34801561030757600080fd5b506103106108cd565b604080519115158252519081900360200190f35b34801561033057600080fd5b506103396108de565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561037357818101518382015260200161035b565b50505050905090810190601f1680156103a05780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156103ba57600080fd5b50610206610915565b3480156103cf57600080fd5b5061015a610924565b3480156103e457600080fd5b506101ae60043561092a565b3480156103fc57600080fd5b50610243600160a060020a0360043516610965565b34801561041d57600080fd5b5061015a6109ab565b34801561043257600080fd5b50610310600160a060020a03600435166109e0565b34801561045357600080fd5b50610243600160a060020a036004358116906024351660443515156109f5565b34801561047f57600080fd5b50610206600160a060020a0360043516610a1c565b3480156104a057600080fd5b50610243600160a060020a0360043516610a37565b3480156104c157600080fd5b50610243600160a060020a0360043516610ac8565b3480156104e257600080fd5b50610243600435610ae4565b6007546000906105169061050a8461271063ffffffff610b0316565b9063ffffffff610b1a16565b92915050565b604080517f766f7465000000000000000000000000000000000000000000000000000000008152905190819003600401902081565b60075481565b6000806000806000610567610c79565b5050506000938452505060066020908152604092839020835160a08101855281548082526001830154600160a060020a03908116948301859052600284015416958201869052600383015460608301819052600490930154608090920182905295929493509091565b600160205260009081526040902054600160a060020a031681565b600554600160a060020a0316331461060257600080fd5b50565b600554600090600160a060020a0316331461061f57600080fd5b604080517f16279055000000000000000000000000000000000000000000000000000000008152600160a060020a0385166004820152905173__Common________________________________916316279055916024808301926020929190829003018186803b15801561069257600080fd5b505af41580156106a6573d6000803e3d6000fd5b505050506040513d60208110156106bc57600080fd5b5051156106c857600080fd5b600160a060020a03841660009081526002602052604090205460ff16806106ef5750600082115b15156106fa57600080fd5b61070384610b33565b151561070e57600080fd5b6007546127101161071e57600080fd5b610727856104ee565b60408051848152602081018390528151929350600160a060020a0380881693908716927fdcbc1c05240f31ff3ad067ef1ee35ce4997762752e3a095284754544f4c709d7928290030190a36040805160a081018252868152600160a060020a03808616602080840191825288831684860190815260608501888152426080870190815260008981526006909452969092209451855591516001808601805492861673ffffffffffffffffffffffffffffffffffffffff199384161790559251600286018054919095169116179092559051600383015591516004909101556007546108179163ffffffff610b1a16565b6007555050505050565b600354600160a060020a031681565b6108386108cd565b151561084357600080fd5b600454604051600091600160a060020a0316907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908390a36004805473ffffffffffffffffffffffffffffffffffffffff19169055565b7f020000000000000000000000000000000000000000000000000000000000000081565b600454600160a060020a031690565b600454600160a060020a0316331490565b60408051808201909152600281527f2323000000000000000000000000000000000000000000000000000000000000602082015281565b600554600160a060020a031681565b61271081565b600660205260009081526040902080546001820154600283015460038401546004909401549293600160a060020a0392831693929091169185565b600554600160a060020a0316331461097c57600080fd5b6003805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b604080517f6865696d64616c6c2d784f5549523000000000000000000000000000000000008152905190819003600f01902081565b60026020526000908152604090205460ff1681565b600554600160a060020a03163314610a0c57600080fd5b610a17838383610b69565b505050565b600060208190529081526040902054600160a060020a031681565b610a3f6108cd565b1515610a4a57600080fd5b600160a060020a0381161515610a5f57600080fd5b600554604051600160a060020a038084169216907f211c9015fc81c0dbd45bd99f0f29fc1c143bfd53442d5ffd722bbbef7a887fe990600090a36005805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b610ad06108cd565b1515610adb57600080fd5b61060281610bfb565b600554600160a060020a03163314610afb57600080fd5b506001600755565b60008083831115610b1357600080fd5b5050900390565b600082820183811015610b2c57600080fd5b9392505050565b6000600160a060020a03821615801590610516575050600160a060020a0390811660009081526020819052604090205416151590565b600160a060020a03838116600081815260208181526040808320805473ffffffffffffffffffffffffffffffffffffffff19908116968916968717909155858452600183528184208054909116851790558383526002909152808220805460ff1916861515179055517f85920d35e6c72f6b2affffa04298b0cecfeba86e4a9f407df661f1cb8ab5e6179190a3505050565b600160a060020a0381161515610c1057600080fd5b600454604051600160a060020a038084169216907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e090600090a36004805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b60a060405190810160405280600081526020016000600160a060020a031681526020016000600160a060020a03168152602001600081526020016000815250905600a165627a7a72305820441856864af3a98c5fd3bd0491fcd003539d5019f205c4ca3e4b291e4bfcbd6b0029";

    public static final String FUNC_ROUNDTYPE = "roundType";

    public static final String FUNC_DEPOSITCOUNT = "depositCount";

    public static final String FUNC_REVERSETOKENS = "reverseTokens";

    public static final String FUNC_WETHTOKEN = "wethToken";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_VOTETYPE = "voteType";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_ISOWNER = "isOwner";

    public static final String FUNC_NETWORKID = "networkId";

    public static final String FUNC_ROOTCHAIN = "rootChain";

    public static final String FUNC_CHILD_BLOCK_INTERVAL = "CHILD_BLOCK_INTERVAL";

    public static final String FUNC_DEPOSITS = "deposits";

    public static final String FUNC_CHAIN = "chain";

    public static final String FUNC_ISERC721 = "isERC721";

    public static final String FUNC_TOKENS = "tokens";

    public static final String FUNC_CHANGEROOTCHAIN = "changeRootChain";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_SETEXITNFTCONTRACT = "setExitNFTContract";

    public static final String FUNC_SETWETHTOKEN = "setWETHToken";

    public static final String FUNC_MAPTOKEN = "mapToken";

    public static final String FUNC_FINALIZECOMMIT = "finalizeCommit";

    public static final String FUNC_NEXTDEPOSITBLOCK = "nextDepositBlock";

    public static final String FUNC_DEPOSITBLOCK = "depositBlock";

    public static final String FUNC_CREATEDEPOSITBLOCK = "createDepositBlock";

    public static final Event DEPOSIT_EVENT = new Event("Deposit", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ROOTCHAINCHANGED_EVENT = new Event("RootChainChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event TOKENMAPPED_EVENT = new Event("TokenMapped", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected DepositManager(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected DepositManager(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected DepositManager(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected DepositManager(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<byte[]> roundType() {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(FUNC_ROUNDTYPE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<BigInteger> depositCount() {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(FUNC_DEPOSITCOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> reverseTokens(String param0) {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(FUNC_REVERSETOKENS, 
                Arrays.<Type>asList(new network.matic.maticj.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> wethToken() {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(FUNC_WETHTOKEN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> renounceOwnership() {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<byte[]> voteType() {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(FUNC_VOTETYPE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes1>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<String> owner() {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Boolean> isOwner() {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(FUNC_ISOWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<byte[]> networkId() {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(FUNC_NETWORKID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<String> rootChain() {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(FUNC_ROOTCHAIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> CHILD_BLOCK_INTERVAL() {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(FUNC_CHILD_BLOCK_INTERVAL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Tuple5<BigInteger, String, String, BigInteger, BigInteger>> deposits(BigInteger param0) {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(FUNC_DEPOSITS, 
                Arrays.<Type>asList(new network.matic.maticj.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple5<BigInteger, String, String, BigInteger, BigInteger>>(function,
                new Callable<Tuple5<BigInteger, String, String, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple5<BigInteger, String, String, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<BigInteger, String, String, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }

    public RemoteCall<byte[]> chain() {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(FUNC_CHAIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<Boolean> isERC721(String param0) {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(FUNC_ISERC721, 
                Arrays.<Type>asList(new network.matic.maticj.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<String> tokens(String param0) {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(FUNC_TOKENS, 
                Arrays.<Type>asList(new network.matic.maticj.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> changeRootChain(String newRootChain) {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(
                FUNC_CHANGEROOTCHAIN, 
                Arrays.<Type>asList(new network.matic.maticj.abi.datatypes.Address(newRootChain)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String newOwner) {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new network.matic.maticj.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<DepositEventResponse> getDepositEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DEPOSIT_EVENT, transactionReceipt);
        ArrayList<DepositEventResponse> responses = new ArrayList<DepositEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DepositEventResponse typedResponse = new DepositEventResponse();
            typedResponse._user = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._token = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._amountOrTokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._depositCount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<DepositEventResponse> depositEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, DepositEventResponse>() {
            @Override
            public DepositEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(DEPOSIT_EVENT, log);
                DepositEventResponse typedResponse = new DepositEventResponse();
                typedResponse._user = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._token = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._amountOrTokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._depositCount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DepositEventResponse> depositEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DEPOSIT_EVENT));
        return depositEventFlowable(filter);
    }

    public List<RootChainChangedEventResponse> getRootChainChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ROOTCHAINCHANGED_EVENT, transactionReceipt);
        ArrayList<RootChainChangedEventResponse> responses = new ArrayList<RootChainChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RootChainChangedEventResponse typedResponse = new RootChainChangedEventResponse();
            typedResponse.previousRootChain = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newRootChain = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RootChainChangedEventResponse> rootChainChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, RootChainChangedEventResponse>() {
            @Override
            public RootChainChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ROOTCHAINCHANGED_EVENT, log);
                RootChainChangedEventResponse typedResponse = new RootChainChangedEventResponse();
                typedResponse.previousRootChain = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newRootChain = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RootChainChangedEventResponse> rootChainChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROOTCHAINCHANGED_EVENT));
        return rootChainChangedEventFlowable(filter);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public List<TokenMappedEventResponse> getTokenMappedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TOKENMAPPED_EVENT, transactionReceipt);
        ArrayList<TokenMappedEventResponse> responses = new ArrayList<TokenMappedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TokenMappedEventResponse typedResponse = new TokenMappedEventResponse();
            typedResponse._rootToken = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._childToken = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TokenMappedEventResponse> tokenMappedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, TokenMappedEventResponse>() {
            @Override
            public TokenMappedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TOKENMAPPED_EVENT, log);
                TokenMappedEventResponse typedResponse = new TokenMappedEventResponse();
                typedResponse._rootToken = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._childToken = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TokenMappedEventResponse> tokenMappedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TOKENMAPPED_EVENT));
        return tokenMappedEventFlowable(filter);
    }

    public RemoteCall<TransactionReceipt> setExitNFTContract(String _nftContract) {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(
                FUNC_SETEXITNFTCONTRACT, 
                Arrays.<Type>asList(new network.matic.maticj.abi.datatypes.Address(_nftContract)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> setWETHToken(String _token) {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(
                FUNC_SETWETHTOKEN, 
                Arrays.<Type>asList(new network.matic.maticj.abi.datatypes.Address(_token)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> mapToken(String _rootToken, String _childToken, Boolean _isERC721) {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(
                FUNC_MAPTOKEN, 
                Arrays.<Type>asList(new network.matic.maticj.abi.datatypes.Address(_rootToken), 
                new network.matic.maticj.abi.datatypes.Address(_childToken), 
                new network.matic.maticj.abi.datatypes.Bool(_isERC721)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> finalizeCommit(BigInteger _currentHeaderBlock) {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(
                FUNC_FINALIZECOMMIT, 
                Arrays.<Type>asList(new network.matic.maticj.abi.datatypes.generated.Uint256(_currentHeaderBlock)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> nextDepositBlock(BigInteger currentHeaderBlock) {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(FUNC_NEXTDEPOSITBLOCK, 
                Arrays.<Type>asList(new network.matic.maticj.abi.datatypes.generated.Uint256(currentHeaderBlock)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Tuple5<BigInteger, String, String, BigInteger, BigInteger>> depositBlock(BigInteger _depositCount) {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(FUNC_DEPOSITBLOCK, 
                Arrays.<Type>asList(new network.matic.maticj.abi.datatypes.generated.Uint256(_depositCount)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple5<BigInteger, String, String, BigInteger, BigInteger>>(function,
                new Callable<Tuple5<BigInteger, String, String, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple5<BigInteger, String, String, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<BigInteger, String, String, BigInteger, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> createDepositBlock(BigInteger _currentHeaderBlock, String _token, String _user, BigInteger _amountOrTokenId) {
        final network.matic.maticj.abi.datatypes.Function function = new network.matic.maticj.abi.datatypes.Function(
                FUNC_CREATEDEPOSITBLOCK, 
                Arrays.<Type>asList(new network.matic.maticj.abi.datatypes.generated.Uint256(_currentHeaderBlock), 
                new network.matic.maticj.abi.datatypes.Address(_token), 
                new network.matic.maticj.abi.datatypes.Address(_user), 
                new network.matic.maticj.abi.datatypes.generated.Uint256(_amountOrTokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static DepositManager load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new DepositManager(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static DepositManager load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new DepositManager(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static DepositManager load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new DepositManager(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static DepositManager load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new DepositManager(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<DepositManager> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(DepositManager.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<DepositManager> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(DepositManager.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<DepositManager> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(DepositManager.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<DepositManager> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(DepositManager.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class DepositEventResponse {
        public String _user;

        public String _token;

        public BigInteger _amountOrTokenId;

        public BigInteger _depositCount;
    }

    public static class RootChainChangedEventResponse {
        public String previousRootChain;

        public String newRootChain;
    }

    public static class OwnershipTransferredEventResponse {
        public String previousOwner;

        public String newOwner;
    }

    public static class TokenMappedEventResponse {
        public String _rootToken;

        public String _childToken;
    }
}
