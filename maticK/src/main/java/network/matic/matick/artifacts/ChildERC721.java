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
import network.matic.matick.abi.datatypes.generated.Bytes4;
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

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the network.matic.matick.core.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.4.1.
 */
public class ChildERC721 extends Contract {
    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";
    public static final String FUNC_GETAPPROVED = "getApproved";
    public static final String FUNC_APPROVE = "approve";
    public static final String FUNC_SAFETRANSFERFROM = "safeTransferFrom";
    public static final String FUNC_PARENT = "parent";
    public static final String FUNC_OWNEROF = "ownerOf";
    public static final String FUNC_PARENTOWNER = "parentOwner";
    public static final String FUNC_BALANCEOF = "balanceOf";
    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";
    public static final String FUNC_OWNER = "owner";
    public static final String FUNC_ISOWNER = "isOwner";
    public static final String FUNC_SETAPPROVALFORALL = "setApprovalForAll";
    public static final String FUNC_DISABLEDHASHES = "disabledHashes";
    public static final String FUNC_GETTRANSFERTYPEDHASH = "getTransferTypedHash";
    public static final String FUNC_ISAPPROVEDFORALL = "isApprovedForAll";
    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";
    public static final String FUNC_TOKEN = "token";
    public static final String FUNC_SETPARENT = "setParent";
    public static final String FUNC_DEPOSIT = "deposit";
    public static final String FUNC_WITHDRAW = "withdraw";
    public static final String FUNC_TRANSFERWITHSIG = "transferWithSig";
    public static final String FUNC_SAFETRANSFERWITHSIG = "safeTransferWithSig";
    public static final String FUNC_TRANSFERFROM = "transferFrom";
    public static final Event LOGTRANSFER_EVENT = new Event("LogTransfer",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>() {
            }));
    public static final Event TRANSFER_EVENT = new Event("Transfer",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>(true) {
            }));
    ;
    public static final Event APPROVAL_EVENT = new Event("Approval",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Uint256>(true) {
            }));
    ;
    public static final Event APPROVALFORALL_EVENT = new Event("ApprovalForAll",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Bool>() {
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
    private static final String BINARY = "0x7f6164647265737320746f6b656e0000000000000000000000000000000000000060a09081527f61646472657373207370656e646572000000000000000000000000000000000060ad527f75696e7432353620616d6f756e744f72546f6b656e496400000000000000000060bc527f627974657333322064617461000000000000000000000000000000000000000060d352603f608081815260df6040819052909290918291908083835b602083106100c95780518252601f1990920191602091820191016100aa565b5181516020939093036101000a600019018019909116921691909117905260405192018290039091206004555034915050801561010557600080fd5b50604051620016a7380380620016a78339810160408181528251602084015191840151606085015160008054600160a060020a031916331780825593969495928501949190910192600160a060020a0316917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908290a36101ae7f01ffc9a70000000000000000000000000000000000000000000000000000000064010000000061023e810204565b6101e07f80ac58cd0000000000000000000000000000000000000000000000000000000064010000000061023e810204565b600160a060020a038316158015906102005750600160a060020a03841615155b151561020b57600080fd5b505060038054600160a060020a03938416600160a060020a031991821617909155600180549290931691161790556102aa565b7fffffffff00000000000000000000000000000000000000000000000000000000808216141561026d57600080fd5b7fffffffff00000000000000000000000000000000000000000000000000000000166000908152600660205260409020805460ff19166001179055565b6113ed80620002ba6000396000f30060806040526004361061013d5763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166301ffc9a78114610142578063081812fc1461018d578063095ea7b3146101c15780631499c592146101e757806323b872dd146102085780632e1a7d4d1461023257806342842e0e1461024a57806347e7ef2414610274578063522479d01461029857806360f96a8f146103075780636352211e1461031c5780637019d41a1461033457806370a0823114610349578063715018a61461037c578063767d4188146103915780638da5cb5b146104005780638f32d59b14610415578063a22cb4651461042a578063acd06cb314610450578063b88d4fde14610468578063bfb208aa146104d7578063e985e9c5146104fe578063f2fde38b14610525578063fc0c546a14610546575b600080fd5b34801561014e57600080fd5b506101797bffffffffffffffffffffffffffffffffffffffffffffffffffffffff196004351661055b565b604080519115158252519081900360200190f35b34801561019957600080fd5b506101a560043561058f565b60408051600160a060020a039092168252519081900360200190f35b3480156101cd57600080fd5b506101e5600160a060020a03600435166024356105c1565b005b3480156101f357600080fd5b506101e5600160a060020a036004351661066a565b34801561021457600080fd5b506101e5600160a060020a03600435811690602435166044356106b8565b34801561023e57600080fd5b506101e56004356107d4565b34801561025657600080fd5b506101e5600160a060020a036004358116906024351660443561086a565b34801561028057600080fd5b506101e5600160a060020a0360043516602435610886565b3480156102a457600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526101a59436949293602493928401919081908401838280828437509497505084359550505060208301359260400135600160a060020a031691506109019050565b34801561031357600080fd5b506101a5610946565b34801561032857600080fd5b506101a5600435610955565b34801561034057600080fd5b506101a561097f565b34801561035557600080fd5b5061036a600160a060020a036004351661098e565b60408051918252519081900360200190f35b34801561038857600080fd5b506101e56109c1565b34801561039d57600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526101a59436949293602493928401919081908401838280828437509497505084359550505060208301359260400135600160a060020a03169150610a1e9050565b34801561040c57600080fd5b506101a5610be0565b34801561042157600080fd5b50610179610bef565b34801561043657600080fd5b506101e5600160a060020a03600435166024351515610c00565b34801561045c57600080fd5b50610179600435610c84565b34801561047457600080fd5b50604080516020601f6064356004818101359283018490048402850184019095528184526101e594600160a060020a038135811695602480359092169560443595369560849401918190840183828082843750949750610c999650505050505050565b3480156104e357600080fd5b5061036a600435602435600160a060020a0360443516610cc1565b34801561050a57600080fd5b50610179600160a060020a0360043581169060243516610df4565b34801561053157600080fd5b506101e5600160a060020a0360043516610e22565b34801561055257600080fd5b506101a5610e41565b7bffffffffffffffffffffffffffffffffffffffffffffffffffffffff191660009081526006602052604090205460ff1690565b600061059a82610e50565b15156105a557600080fd5b50600090815260086020526040902054600160a060020a031690565b60006105cc82610955565b9050600160a060020a0383811690821614156105e757600080fd5b33600160a060020a038216148061060357506106038133610df4565b151561060e57600080fd5b6000828152600860205260408082208054600160a060020a031916600160a060020a0387811691821790925591518593918516917f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b92591a4505050565b600354600160a060020a0316331461068157600080fd5b600160a060020a038116151561069657600080fd5b60028054600160a060020a031916600160a060020a0392909216919091179055565b600254600160a060020a0316158015906107705750600254604080517f1ffb811f000000000000000000000000000000000000000000000000000000008152336004820152600160a060020a0385811660248301526044820185905291519190921691631ffb811f9160648083019260209291908290030181600087803b15801561074257600080fd5b505af1158015610756573d6000803e3d6000fd5b505050506040513d602081101561076c57600080fd5b5051155b1561077a576107cf565b610785838383610e6d565b600154604080518381529051600160a060020a0380861693878216939116917f6eabe333476233fd382224f233210cb808a7bc4c4de64f9d76628bf63c677b1a9181900360200190a45b505050565b600080336107e184610955565b600160a060020a0316146107f457600080fd5b3391506108008261098e565b905061080c8284610efb565b600154600160a060020a0380841691167febff2602b3f468259e1e99f613fed6691f3a6526effe6ef3e768ba7ae7a36c4f85846108488761098e565b60408051938452602084019290925282820152519081900360600190a3505050565b6107cf8383836020604051908101604052806000815250610c99565b6000610890610bef565b151561089b57600080fd5b600160a060020a03831615156108b057600080fd5b6108b98361098e565b90506108c58383610f4b565b600154600160a060020a0380851691167f4e2ca0515ed1aef1395f66b5303bb5d6f1bf9d61a353fa53f73f8ac9973fa9f684846108488861098e565b60008061091086868686610a1e565b905061092e8184876020604051908101604052806000815250610fa6565b151561093957600080fd5b8091505b50949350505050565b600254600160a060020a031681565b600081815260076020526040812054600160a060020a031680151561097957600080fd5b92915050565b600354600160a060020a031681565b6000600160a060020a03821615156109a557600080fd5b50600160a060020a031660009081526009602052604090205490565b6109c9610bef565b15156109d457600080fd5b60008054604051600160a060020a03909116907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908390a360008054600160a060020a0319169055565b6000806000610a2e868633610cc1565b60008181526005602052604090205490925060ff1615610aaf57604080517f08c379a000000000000000000000000000000000000000000000000000000000815260206004820152600f60248201527f5369672064656163746976617465640000000000000000000000000000000000604482015290519081900360640190fd5b6000828152600560209081526040808320805460ff1916600117905580517f77d32e9400000000000000000000000000000000000000000000000000000000815260048101868152602482019283528b5160448301528b5173__ECVerify______________________________956377d32e949589958f959093606490910192918601918190849084905b83811015610b52578181015183820152602001610b3a565b50505050905090810190601f168015610b7f5780820380516001836020036101000a031916815260200191505b50935050505060206040518083038186803b158015610b9d57600080fd5b505af4158015610bb1573d6000803e3d6000fd5b505050506040513d6020811015610bc757600080fd5b50519050610bd6818588611123565b9695505050505050565b600054600160a060020a031690565b600054600160a060020a0316331490565b600160a060020a038216331415610c1657600080fd5b336000818152600a60209081526040808320600160a060020a03871680855290835292819020805460ff1916861515908117909155815190815290519293927f17307eab39ab6107e8899845ad3d59bd9653f200f220920489ca2b5937696c31929181900390910190a35050565b60056020526000908152604090205460ff1681565b610ca48484846106b8565b610cb084848484610fa6565b1515610cbb57600080fd5b50505050565b600454604080516c01000000000000000000000000308102602080840191909152600160a060020a038616909102603483015260488201879052606880830187905283518084039091018152608890920192839052815160009493918291908401908083835b60208310610d465780518252601f199092019160209182019101610d27565b51815160209384036101000a6000190180199092169116179052604080519290940182900382208282019790975281840196909652825180820384018152606090910192839052805190959294508493509185019190508083835b60208310610dc05780518252601f199092019160209182019101610da1565b5181516020939093036101000a60001901801990911692169190911790526040519201829003909120979650505050505050565b600160a060020a039182166000908152600a6020908152604080832093909416825291909152205460ff1690565b610e2a610bef565b1515610e3557600080fd5b610e3e81611149565b50565b600154600160a060020a031681565b600090815260076020526040902054600160a060020a0316151590565b610e7733826111b9565b1515610e8257600080fd5b600160a060020a0382161515610e9757600080fd5b610ea18382611218565b610eab838261127c565b610eb58282611305565b8082600160a060020a031684600160a060020a03167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef60405160405180910390a4505050565b610f058282611218565b610f0f828261127c565b6040518190600090600160a060020a038516907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef908390a45050565b600160a060020a0382161515610f6057600080fd5b610f6a8282611305565b6040518190600160a060020a038416906000907fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef908290a45050565b600080610fbb85600160a060020a0316611389565b1515610fca576001915061093d565b6040517f150b7a020000000000000000000000000000000000000000000000000000000081523360048201818152600160a060020a03898116602485015260448401889052608060648501908152875160848601528751918a169463150b7a0294938c938b938b93909160a490910190602085019080838360005b8381101561105d578181015183820152602001611045565b50505050905090810190601f16801561108a5780820380516001836020036101000a031916815260200191505b5095505050505050602060405180830381600087803b1580156110ac57600080fd5b505af11580156110c0573d6000803e3d6000fd5b505050506040513d60208110156110d657600080fd5b50517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167f150b7a0200000000000000000000000000000000000000000000000000000000149695505050505050565b82600160a060020a031661113682610955565b600160a060020a031614610e8257600080fd5b600160a060020a038116151561115e57600080fd5b60008054604051600160a060020a03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a360008054600160a060020a031916600160a060020a0392909216919091179055565b6000806111c583610955565b905080600160a060020a031684600160a060020a03161480611200575083600160a060020a03166111f58461058f565b600160a060020a0316145b8061121057506112108185610df4565b949350505050565b81600160a060020a031661122b82610955565b600160a060020a03161461123e57600080fd5b600081815260086020526040902054600160a060020a0316156112785760008181526008602052604090208054600160a060020a03191690555b5050565b81600160a060020a031661128f82610955565b600160a060020a0316146112a257600080fd5b600160a060020a0382166000908152600960205260409020546112cc90600163ffffffff61139116565b600160a060020a039092166000908152600960209081526040808320949094559181526007909152208054600160a060020a0319169055565b600081815260076020526040902054600160a060020a03161561132757600080fd5b60008181526007602090815260408083208054600160a060020a031916600160a060020a038716908117909155835260099091529020546113699060016113a8565b600160a060020a0390921660009081526009602052604090209190915550565b6000903b1190565b600080838311156113a157600080fd5b5050900390565b6000828201838110156113ba57600080fd5b93925050505600a165627a7a723058205dd6c70f176758a59dc20851ec1dda41d5a81fc2faeada0711ab946cb9954dd00029";

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected ChildERC721(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ChildERC721(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ChildERC721(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ChildERC721(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @Deprecated
    public static ChildERC721 load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ChildERC721(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ChildERC721 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ChildERC721(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ChildERC721 load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ChildERC721(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ChildERC721 load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ChildERC721(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ChildERC721> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _owner, String _token, String name, String symbol) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(_owner),
                new Address(_token),
                new Utf8String(name),
                new Utf8String(symbol)));
        return deployRemoteCall(ChildERC721.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<ChildERC721> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _owner, String _token, String name, String symbol) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(_owner),
                new Address(_token),
                new Utf8String(name),
                new Utf8String(symbol)));
        return deployRemoteCall(ChildERC721.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ChildERC721> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _owner, String _token, String name, String symbol) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(_owner),
                new Address(_token),
                new Utf8String(name),
                new Utf8String(symbol)));
        return deployRemoteCall(ChildERC721.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ChildERC721> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _owner, String _token, String name, String symbol) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(_owner),
                new Address(_token),
                new Utf8String(name),
                new Utf8String(symbol)));
        return deployRemoteCall(ChildERC721.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public RemoteCall<Boolean> supportsInterface(byte[] interfaceId) {
        final Function function = new Function(FUNC_SUPPORTSINTERFACE,
                Arrays.<Type>asList(new Bytes4(interfaceId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<String> getApproved(BigInteger tokenId) {
        final Function function = new Function(FUNC_GETAPPROVED,
                Arrays.<Type>asList(new Uint256(tokenId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> approve(String to, BigInteger tokenId) {
        final Function function = new Function(
                FUNC_APPROVE,
                Arrays.<Type>asList(new Address(to),
                        new Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger tokenId) {
        final Function function = new Function(
                FUNC_SAFETRANSFERFROM,
                Arrays.<Type>asList(new Address(from),
                        new Address(to),
                        new Uint256(tokenId)),
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

    public RemoteCall<String> ownerOf(BigInteger tokenId) {
        final Function function = new Function(FUNC_OWNEROF,
                Arrays.<Type>asList(new Uint256(tokenId)),
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

    public RemoteCall<TransactionReceipt> setApprovalForAll(String to, Boolean approved) {
        final Function function = new Function(
                FUNC_SETAPPROVALFORALL,
                Arrays.<Type>asList(new Address(to),
                        new Bool(approved)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> disabledHashes(byte[] param0) {
        final Function function = new Function(FUNC_DISABLEDHASHES,
                Arrays.<Type>asList(new Bytes32(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> safeTransferFrom(String from, String to, BigInteger tokenId, byte[] _data) {
        final Function function = new Function(
                FUNC_SAFETRANSFERFROM,
                Arrays.<Type>asList(new Address(from),
                        new Address(to),
                        new Uint256(tokenId),
                        new DynamicBytes(_data)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<byte[]> getTransferTypedHash(BigInteger amount, byte[] data, String spender) {
        final Function function = new Function(FUNC_GETTRANSFERTYPEDHASH,
                Arrays.<Type>asList(new Uint256(amount),
                        new Bytes32(data),
                        new Address(spender)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<Boolean> isApprovedForAll(String owner, String operator) {
        final Function function = new Function(FUNC_ISAPPROVEDFORALL,
                Arrays.<Type>asList(new Address(owner),
                        new Address(operator)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
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

    public RemoteCall<TransactionReceipt> deposit(String user, BigInteger tokenId) {
        final Function function = new Function(
                FUNC_DEPOSIT,
                Arrays.<Type>asList(new Address(user),
                        new Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> withdraw(BigInteger tokenId) {
        final Function function = new Function(
                FUNC_WITHDRAW,
                Arrays.<Type>asList(new Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferWithSig(byte[] sig, BigInteger tokenId, byte[] data, String to) {
        final Function function = new Function(
                FUNC_TRANSFERWITHSIG,
                Arrays.<Type>asList(new DynamicBytes(sig),
                        new Uint256(tokenId),
                        new Bytes32(data),
                        new Address(to)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> safeTransferWithSig(byte[] sig, BigInteger tokenId, byte[] data, String to) {
        final Function function = new Function(
                FUNC_SAFETRANSFERWITHSIG,
                Arrays.<Type>asList(new DynamicBytes(sig),
                        new Uint256(tokenId),
                        new Bytes32(data),
                        new Address(to)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferFrom(String from, String to, BigInteger tokenId) {
        final Function function = new Function(
                FUNC_TRANSFERFROM,
                Arrays.<Type>asList(new Address(from),
                        new Address(to),
                        new Uint256(tokenId)),
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
    }

    public static class TransferEventResponse {
        public String from;

        public String to;

        public BigInteger tokenId;
    }

    public static class ApprovalEventResponse {
        public String owner;

        public String approved;

        public BigInteger tokenId;
    }

    public static class ApprovalForAllEventResponse {
        public String owner;

        public String operator;

        public Boolean approved;
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
