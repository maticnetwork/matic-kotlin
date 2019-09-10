package network.matic.matick.artifacts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import network.matic.matick.abi.EventEncoder;
import network.matic.matick.abi.FunctionEncoder;
import network.matic.matick.abi.TypeReference;
import network.matic.matick.abi.datatypes.Address;
import network.matic.matick.abi.datatypes.Bool;
import network.matic.matick.abi.datatypes.DynamicBytes;
import network.matic.matick.abi.datatypes.Event;
import network.matic.matick.abi.datatypes.Function;
import network.matic.matick.abi.datatypes.Type;
import network.matic.matick.abi.datatypes.Utf8String;
import network.matic.matick.abi.datatypes.generated.Bytes32;
import network.matic.matick.abi.datatypes.generated.Uint256;
import network.matic.matick.abi.datatypes.generated.Uint8;
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

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.4.1.
 */
public class ChildERC20 extends Contract {
    public static final String FUNC_NAME = "name";
    public static final String FUNC_APPROVE = "approve";
    public static final String FUNC_TOTALSUPPLY = "totalSupply";
    public static final String FUNC_DECIMALS = "decimals";
    public static final String FUNC_INCREASEALLOWANCE = "increaseAllowance";
    public static final String FUNC_PARENT = "parent";
    public static final String FUNC_PARENTOWNER = "parentOwner";
    public static final String FUNC_BALANCEOF = "balanceOf";
    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";
    public static final String FUNC_OWNER = "owner";
    public static final String FUNC_ISOWNER = "isOwner";
    public static final String FUNC_SYMBOL = "symbol";
    public static final String FUNC_DECREASEALLOWANCE = "decreaseAllowance";
    public static final String FUNC_DISABLEDHASHES = "disabledHashes";
    public static final String FUNC_GETTRANSFERTYPEDHASH = "getTransferTypedHash";
    public static final String FUNC_ALLOWANCE = "allowance";
    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";
    public static final String FUNC_TOKEN = "token";
    public static final String FUNC_SETPARENT = "setParent";
    public static final String FUNC_DEPOSIT = "deposit";
    public static final String FUNC_WITHDRAW = "withdraw";
    public static final String FUNC_TRANSFER = "transfer";
    public static final String FUNC_TRANSFERWITHSIG = "transferWithSig";
    public static final String FUNC_TRANSFERFROM = "transferFrom";
    public static final Event LOGTRANSFER_EVENT = new Event("LogTransfer",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }));
    public static final Event TRANSFER_EVENT = new Event("Transfer",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));
    ;
    public static final Event APPROVAL_EVENT = new Event("Approval",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));
    ;
    public static final Event DEPOSIT_EVENT = new Event("Deposit",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }));
    ;
    public static final Event WITHDRAW_EVENT = new Event("Withdraw",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Uint256>() {
            }));
    ;
    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }));
    ;
    protected static final HashMap<String, String> _addresses;
    ;
    private static final String BINARY = "0x7f6164647265737320746f6b656e0000000000000000000000000000000000000060a09081527f61646472657373207370656e646572000000000000000000000000000000000060ad527f75696e7432353620616d6f756e744f72546f6b656e496400000000000000000060bc527f627974657333322064617461000000000000000000000000000000000000000060d352603f608081815260df6040819052909290918291908083835b60208310620000cb5780518252601f199092019160209182019101620000aa565b5181516020939093036101000a60001901801990911692169190911790526040519201829003909120600455503491505080156200010857600080fd5b50604051620016383803806200163883398101604081815282516020840151918401516060850151608086015160008054600160a060020a0319163317808255949795969386019592909201939092859285928592600160a060020a039190911691907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908290a38251620001a590600990602086019062000238565b508151620001bb90600a90602085019062000238565b50600b805460ff191660ff929092169190911790555050600160a060020a03841615801590620001f35750600160a060020a03851615155b1515620001ff57600080fd5b505060038054600160a060020a03948516600160a060020a031991821617909155600180549390941692169190911790915550620002dd565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200027b57805160ff1916838001178555620002ab565b82800160010185558215620002ab579182015b82811115620002ab5782518255916020019190600101906200028e565b50620002b9929150620002bd565b5090565b620002da91905b80821115620002b95760008155600101620002c4565b90565b61134b80620002ed6000396000f30060806040526004361061013d5763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166306fdde038114610142578063095ea7b3146101cc5780631499c5921461020457806318160ddd1461022757806323b872dd1461024e5780632e1a7d4d14610278578063313ce5671461029057806339509351146102bb57806347e7ef24146102df57806360f96a8f146103035780637019d41a1461033457806370a0823114610349578063715018a61461036a578063767d41881461037f5780638da5cb5b146103ee5780638f32d59b1461040357806395d89b4114610418578063a457c2d71461042d578063a9059cbb14610451578063acd06cb314610475578063bfb208aa1461048d578063dd62ed3e146104b4578063f2fde38b146104db578063fc0c546a146104fc575b600080fd5b34801561014e57600080fd5b50610157610511565b6040805160208082528351818301528351919283929083019185019080838360005b83811015610191578181015183820152602001610179565b50505050905090810190601f1680156101be5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156101d857600080fd5b506101f0600160a060020a03600435166024356105a7565b604080519115158252519081900360200190f35b34801561021057600080fd5b50610225600160a060020a0360043516610625565b005b34801561023357600080fd5b5061023c610680565b60408051918252519081900360200190f35b34801561025a57600080fd5b506101f0600160a060020a0360043581169060243516604435610686565b34801561028457600080fd5b5061022560043561072e565b34801561029c57600080fd5b506102a56107bf565b6040805160ff9092168252519081900360200190f35b3480156102c757600080fd5b506101f0600160a060020a03600435166024356107c8565b3480156102eb57600080fd5b50610225600160a060020a0360043516602435610878565b34801561030f57600080fd5b50610318610901565b60408051600160a060020a039092168252519081900360200190f35b34801561034057600080fd5b50610318610910565b34801561035557600080fd5b5061023c600160a060020a036004351661091f565b34801561037657600080fd5b5061022561093a565b34801561038b57600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526103189436949293602493928401919081908401838280828437509497505084359550505060208301359260400135600160a060020a031691506109a49050565b3480156103fa57600080fd5b50610318610b71565b34801561040f57600080fd5b506101f0610b80565b34801561042457600080fd5b50610157610b91565b34801561043957600080fd5b506101f0600160a060020a0360043516602435610bf2565b34801561045d57600080fd5b506101f0600160a060020a0360043516602435610c3d565b34801561048157600080fd5b506101f0600435610dae565b34801561049957600080fd5b5061023c600435602435600160a060020a0360443516610dc3565b3480156104c057600080fd5b5061023c600160a060020a0360043581169060243516610ef6565b3480156104e757600080fd5b50610225600160a060020a0360043516610f21565b34801561050857600080fd5b50610318610f40565b60098054604080516020601f600260001961010060018816150201909516949094049384018190048102820181019092528281526060939092909183018282801561059d5780601f106105725761010080835404028352916020019161059d565b820191906000526020600020905b81548152906001019060200180831161058057829003601f168201915b5050505050905090565b6000600160a060020a03831615156105be57600080fd5b336000818152600760209081526040808320600160a060020a03881680855290835292819020869055805186815290519293927f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925929181900390910190a350600192915050565b600354600160a060020a0316331461063c57600080fd5b600160a060020a038116151561065157600080fd5b6002805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b60085490565b6000806000806106958761091f565b92506106a08661091f565b91506106ad878787610f4f565b600154909150600160a060020a038088169189821691167fe6497e3ee548a3372136af2fcb0696db31fc6cf20260707645068bd3fe97f3c48887876106f18e61091f565b6106fa8e61091f565b6040805195865260208601949094528484019290925260608401526080830152519081900360a00190a49695505050505050565b33600061073a8261091f565b905060008311801561074c5750828110155b151561075757600080fd5b6107618284610fec565b600154600160a060020a0380841691167febff2602b3f468259e1e99f613fed6691f3a6526effe6ef3e768ba7ae7a36c4f858461079d8761091f565b60408051938452602084019290925282820152519081900360600190a3505050565b600b5460ff1690565b6000600160a060020a03831615156107df57600080fd5b336000908152600760209081526040808320600160a060020a0387168452909152902054610813908363ffffffff6110bc16565b336000818152600760209081526040808320600160a060020a0389168085529083529281902085905580519485525191937f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925929081900390910190a350600192915050565b6000610882610b80565b151561088d57600080fd5b6000821180156108a55750600160a060020a03831615155b15156108b057600080fd5b6108b98361091f565b90506108c583836110d5565b600154600160a060020a0380851691167f4e2ca0515ed1aef1395f66b5303bb5d6f1bf9d61a353fa53f73f8ac9973fa9f6848461079d8861091f565b600254600160a060020a031681565b600354600160a060020a031681565b600160a060020a031660009081526006602052604090205490565b610942610b80565b151561094d57600080fd5b60008054604051600160a060020a03909116907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908390a36000805473ffffffffffffffffffffffffffffffffffffffff19169055565b600080808086116109b457600080fd5b6109bf868633610dc3565b60008181526005602052604090205490925060ff1615610a4057604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152600f60248201527f5369672064656163746976617465640000000000000000000000000000000000604482015290519081900360640190fd5b6000828152600560209081526040808320805460ff1916600117905580517f77d32e9400000000000000000000000000000000000000000000000000000000815260048101868152602482019283528b5160448301528b5173__ECVerify______________________________956377d32e949589958f959093606490910192918601918190849084905b83811015610ae3578181015183820152602001610acb565b50505050905090810190601f168015610b105780820380516001836020036101000a031916815260200191505b50935050505060206040518083038186803b158015610b2e57600080fd5b505af4158015610b42573d6000803e3d6000fd5b505050506040513d6020811015610b5857600080fd5b50519050610b67818588611181565b9695505050505050565b600054600160a060020a031690565b600054600160a060020a0316331490565b600a8054604080516020601f600260001961010060018816150201909516949094049384018190048102820181019092528281526060939092909183018282801561059d5780601f106105725761010080835404028352916020019161059d565b6000600160a060020a0383161515610c0957600080fd5b336000908152600760209081526040808320600160a060020a0387168452909152902054610813908363ffffffff61127516565b600254600090819081908190600160a060020a031615801590610cfe5750600254604080517f1ffb811f000000000000000000000000000000000000000000000000000000008152336004820152600160a060020a0389811660248301526044820189905291519190921691631ffb811f9160648083019260209291908290030181600087803b158015610cd057600080fd5b505af1158015610ce4573d6000803e3d6000fd5b505050506040513d6020811015610cfa57600080fd5b5051155b15610d0c5760009350610da5565b610d153361091f565b9250610d208661091f565b9150610d2c868661128c565b600154909150600160a060020a03808816913391167fe6497e3ee548a3372136af2fcb0696db31fc6cf20260707645068bd3fe97f3c4888787610d6e8661091f565b610d778e61091f565b6040805195865260208601949094528484019290925260608401526080830152519081900360a00190a48093505b50505092915050565b60056020526000908152604090205460ff1681565b600454604080516c01000000000000000000000000308102602080840191909152600160a060020a038616909102603483015260488201879052606880830187905283518084039091018152608890920192839052815160009493918291908401908083835b60208310610e485780518252601f199092019160209182019101610e29565b51815160209384036101000a6000190180199092169116179052604080519290940182900382208282019790975281840196909652825180820384018152606090910192839052805190959294508493509185019190508083835b60208310610ec25780518252601f199092019160209182019101610ea3565b5181516020939093036101000a60001901801990911692169190911790526040519201829003909120979650505050505050565b600160a060020a03918216600090815260076020908152604080832093909416825291909152205490565b610f29610b80565b1515610f3457600080fd5b610f3d816112a2565b50565b600154600160a060020a031681565b600160a060020a0383166000908152600760209081526040808320338452909152812054821115610f7f57600080fd5b600160a060020a0384166000908152600760209081526040808320338452909152902054610fb3908363ffffffff61127516565b600160a060020a0385166000908152600760209081526040808320338452909152902055610fe2848484611181565b5060019392505050565b600160a060020a038216151561100157600080fd5b600160a060020a03821660009081526006602052604090205481111561102657600080fd5b600854611039908263ffffffff61127516565b600855600160a060020a038216600090815260066020526040902054611065908263ffffffff61127516565b600160a060020a0383166000818152600660209081526040808320949094558351858152935191937fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef929081900390910190a35050565b6000828201838110156110ce57600080fd5b9392505050565b600160a060020a03821615156110ea57600080fd5b6008546110fd908263ffffffff6110bc16565b600855600160a060020a038216600090815260066020526040902054611129908263ffffffff6110bc16565b600160a060020a03831660008181526006602090815260408083209490945583518581529351929391927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9281900390910190a35050565b600160a060020a0383166000908152600660205260409020548111156111a657600080fd5b600160a060020a03821615156111bb57600080fd5b600160a060020a0383166000908152600660205260409020546111e4908263ffffffff61127516565b600160a060020a038085166000908152600660205260408082209390935590841681522054611219908263ffffffff6110bc16565b600160a060020a0380841660008181526006602090815260409182902094909455805185815290519193928716927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef92918290030190a3505050565b6000808383111561128557600080fd5b5050900390565b6000611299338484611181565b50600192915050565b600160a060020a03811615156112b757600080fd5b60008054604051600160a060020a03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a36000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03929092169190911790555600a165627a7a72305820353e42b1ebd1dce886e96c89e5a7bf146c73978005004352a29087176044a9b00029";

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected ChildERC20(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ChildERC20(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ChildERC20(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ChildERC20(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @Deprecated
    public static ChildERC20 load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ChildERC20(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ChildERC20 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ChildERC20(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ChildERC20 load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ChildERC20(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ChildERC20 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ChildERC20(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ChildERC20> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _owner, String _token, String _name, String _symbol, BigInteger _decimals) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(_owner),
                new Address(_token),
                new Utf8String(_name),
                new Utf8String(_symbol),
                new network.matic.matick.abi.datatypes.generated.Uint8(_decimals)));
        return deployRemoteCall(ChildERC20.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<ChildERC20> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _owner, String _token, String _name, String _symbol, BigInteger _decimals) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(_owner),
                new Address(_token),
                new Utf8String(_name),
                new Utf8String(_symbol),
                new network.matic.matick.abi.datatypes.generated.Uint8(_decimals)));
        return deployRemoteCall(ChildERC20.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ChildERC20> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _owner, String _token, String _name, String _symbol, BigInteger _decimals) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(_owner),
                new Address(_token),
                new Utf8String(_name),
                new Utf8String(_symbol),
                new network.matic.matick.abi.datatypes.generated.Uint8(_decimals)));
        return deployRemoteCall(ChildERC20.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ChildERC20> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _owner, String _token, String _name, String _symbol, BigInteger _decimals) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(_owner),
                new Address(_token),
                new Utf8String(_name),
                new Utf8String(_symbol),
                new network.matic.matick.abi.datatypes.generated.Uint8(_decimals)));
        return deployRemoteCall(ChildERC20.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public RemoteCall<String> name() {
        final Function function = new Function(FUNC_NAME,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> approve(String spender, BigInteger value) {
        final Function function = new Function(
                FUNC_APPROVE,
                Arrays.<Type>asList(new Address(spender),
                        new network.matic.matick.abi.datatypes.generated.Uint256(value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> totalSupply() {
        final Function function = new Function(FUNC_TOTALSUPPLY,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> decimals() {
        final Function function = new Function(FUNC_DECIMALS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> increaseAllowance(String spender, BigInteger addedValue) {
        final Function function = new Function(
                FUNC_INCREASEALLOWANCE,
                Arrays.<Type>asList(new Address(spender),
                        new network.matic.matick.abi.datatypes.generated.Uint256(addedValue)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> parent() {
        final Function function = new Function(FUNC_PARENT,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> parentOwner() {
        final Function function = new Function(FUNC_PARENTOWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> balanceOf(String owner) {
        final Function function = new Function(FUNC_BALANCEOF,
                Arrays.<Type>asList(new Address(owner)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> renounceOwnership() {
        final Function function = new Function(
                FUNC_RENOUNCEOWNERSHIP,
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Boolean> isOwner() {
        final Function function = new Function(FUNC_ISOWNER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<String> symbol() {
        final Function function = new Function(FUNC_SYMBOL,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> decreaseAllowance(String spender, BigInteger subtractedValue) {
        final Function function = new Function(
                FUNC_DECREASEALLOWANCE,
                Arrays.<Type>asList(new Address(spender),
                        new network.matic.matick.abi.datatypes.generated.Uint256(subtractedValue)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> disabledHashes(byte[] param0) {
        final Function function = new Function(FUNC_DISABLEDHASHES,
                Arrays.<Type>asList(new network.matic.matick.abi.datatypes.generated.Bytes32(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<byte[]> getTransferTypedHash(BigInteger amount, byte[] data, String spender) {
        final Function function = new Function(FUNC_GETTRANSFERTYPEDHASH,
                Arrays.<Type>asList(new network.matic.matick.abi.datatypes.generated.Uint256(amount),
                        new network.matic.matick.abi.datatypes.generated.Bytes32(data),
                        new Address(spender)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<BigInteger> allowance(String owner, String spender) {
        final Function function = new Function(FUNC_ALLOWANCE,
                Arrays.<Type>asList(new Address(owner),
                        new Address(spender)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP,
                Arrays.<Type>asList(new Address(newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> token() {
        final Function function = new Function(FUNC_TOKEN,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public List<LogTransferEventResponse> getLogTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(LOGTRANSFER_EVENT, transactionReceipt);
        ArrayList<LogTransferEventResponse> responses = new ArrayList<LogTransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogTransferEventResponse typedResponse = new LogTransferEventResponse();
            typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.amountOrTokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.input1 = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.input2 = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.output1 = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.output2 = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogTransferEventResponse> logTransferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, LogTransferEventResponse>() {
            @Override
            public LogTransferEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(LOGTRANSFER_EVENT, log);
                LogTransferEventResponse typedResponse = new LogTransferEventResponse();
                typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.amountOrTokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.input1 = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.input2 = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.output1 = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.output2 = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogTransferEventResponse> logTransferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGTRANSFER_EVENT));
        return logTransferEventFlowable(filter);
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
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, TransferEventResponse>() {
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
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ApprovalEventResponse>() {
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

    public List<DepositEventResponse> getDepositEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(DEPOSIT_EVENT, transactionReceipt);
        ArrayList<DepositEventResponse> responses = new ArrayList<DepositEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            DepositEventResponse typedResponse = new DepositEventResponse();
            typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amountOrTokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.input1 = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.output1 = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
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
                typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.amountOrTokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.input1 = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.output1 = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<DepositEventResponse> depositEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DEPOSIT_EVENT));
        return depositEventFlowable(filter);
    }

    public List<WithdrawEventResponse> getWithdrawEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(WITHDRAW_EVENT, transactionReceipt);
        ArrayList<WithdrawEventResponse> responses = new ArrayList<WithdrawEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WithdrawEventResponse typedResponse = new WithdrawEventResponse();
            typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.amountOrTokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.input1 = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.output1 = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<WithdrawEventResponse> withdrawEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, WithdrawEventResponse>() {
            @Override
            public WithdrawEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WITHDRAW_EVENT, log);
                WithdrawEventResponse typedResponse = new WithdrawEventResponse();
                typedResponse.token = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.from = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.amountOrTokenId = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.input1 = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.output1 = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<WithdrawEventResponse> withdrawEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WITHDRAW_EVENT));
        return withdrawEventFlowable(filter);
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

    public RemoteCall<TransactionReceipt> setParent(String _parent) {
        final Function function = new Function(
                FUNC_SETPARENT,
                Arrays.<Type>asList(new Address(_parent)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> deposit(String user, BigInteger amount) {
        final Function function = new Function(
                FUNC_DEPOSIT,
                Arrays.<Type>asList(new Address(user),
                        new network.matic.matick.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> withdraw(BigInteger amount) {
        final Function function = new Function(
                FUNC_WITHDRAW,
                Arrays.<Type>asList(new network.matic.matick.abi.datatypes.generated.Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transfer(String to, BigInteger value) {
        final Function function = new Function(
                FUNC_TRANSFER,
                Arrays.<Type>asList(new Address(to),
                        new network.matic.matick.abi.datatypes.generated.Uint256(value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferWithSig(byte[] sig, BigInteger amount, byte[] data, String to) {
        final Function function = new Function(
                FUNC_TRANSFERWITHSIG,
                Arrays.<Type>asList(new DynamicBytes(sig),
                        new network.matic.matick.abi.datatypes.generated.Uint256(amount),
                        new network.matic.matick.abi.datatypes.generated.Bytes32(data),
                        new Address(to)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferFrom(String from, String to, BigInteger value) {
        final Function function = new Function(
                FUNC_TRANSFERFROM,
                Arrays.<Type>asList(new Address(from),
                        new Address(to),
                        new network.matic.matick.abi.datatypes.generated.Uint256(value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class LogTransferEventResponse {
        public String token;

        public String from;

        public String to;

        public BigInteger amountOrTokenId;

        public BigInteger input1;

        public BigInteger input2;

        public BigInteger output1;

        public BigInteger output2;
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

    public static class DepositEventResponse {
        public String token;

        public String from;

        public BigInteger amountOrTokenId;

        public BigInteger input1;

        public BigInteger output1;
    }

    public static class WithdrawEventResponse {
        public String token;

        public String from;

        public BigInteger amountOrTokenId;

        public BigInteger input1;

        public BigInteger output1;
    }

    public static class OwnershipTransferredEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
