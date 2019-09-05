package network.matic.maticj.core.protocol.core;

import network.matic.maticj.core.protocol.core.methods.request.ShhFilter;
import network.matic.maticj.core.protocol.core.methods.request.Transaction;
import network.matic.maticj.core.protocol.core.methods.response.DbGetHex;
import network.matic.maticj.core.protocol.core.methods.response.DbGetString;
import network.matic.maticj.core.protocol.core.methods.response.DbPutHex;
import network.matic.maticj.core.protocol.core.methods.response.DbPutString;
import network.matic.maticj.core.protocol.core.methods.response.EthAccounts;
import network.matic.maticj.core.protocol.core.methods.response.EthBlock;
import network.matic.maticj.core.protocol.core.methods.response.EthBlockNumber;
import network.matic.maticj.core.protocol.core.methods.response.EthCall;
import network.matic.maticj.core.protocol.core.methods.response.EthCoinbase;
import network.matic.maticj.core.protocol.core.methods.response.EthCompileLLL;
import network.matic.maticj.core.protocol.core.methods.response.EthCompileSerpent;
import network.matic.maticj.core.protocol.core.methods.response.EthCompileSolidity;
import network.matic.maticj.core.protocol.core.methods.response.EthEstimateGas;
import network.matic.maticj.core.protocol.core.methods.response.EthFilter;
import network.matic.maticj.core.protocol.core.methods.response.EthGasPrice;
import network.matic.maticj.core.protocol.core.methods.response.EthGetBalance;
import network.matic.maticj.core.protocol.core.methods.response.EthGetBlockTransactionCountByHash;
import network.matic.maticj.core.protocol.core.methods.response.EthGetBlockTransactionCountByNumber;
import network.matic.maticj.core.protocol.core.methods.response.EthGetCode;
import network.matic.maticj.core.protocol.core.methods.response.EthGetCompilers;
import network.matic.maticj.core.protocol.core.methods.response.EthGetStorageAt;
import network.matic.maticj.core.protocol.core.methods.response.EthGetTransactionCount;
import network.matic.maticj.core.protocol.core.methods.response.EthGetTransactionReceipt;
import network.matic.maticj.core.protocol.core.methods.response.EthGetUncleCountByBlockHash;
import network.matic.maticj.core.protocol.core.methods.response.EthGetUncleCountByBlockNumber;
import network.matic.maticj.core.protocol.core.methods.response.EthGetWork;
import network.matic.maticj.core.protocol.core.methods.response.EthHashrate;
import network.matic.maticj.core.protocol.core.methods.response.EthLog;
import network.matic.maticj.core.protocol.core.methods.response.EthMining;
import network.matic.maticj.core.protocol.core.methods.response.EthProtocolVersion;
import network.matic.maticj.core.protocol.core.methods.response.EthSendTransaction;
import network.matic.maticj.core.protocol.core.methods.response.EthSign;
import network.matic.maticj.core.protocol.core.methods.response.EthSubmitHashrate;
import network.matic.maticj.core.protocol.core.methods.response.EthSubmitWork;
import network.matic.maticj.core.protocol.core.methods.response.EthSyncing;
import network.matic.maticj.core.protocol.core.methods.response.EthTransaction;
import network.matic.maticj.core.protocol.core.methods.response.EthUninstallFilter;
import network.matic.maticj.core.protocol.core.methods.response.NetListening;
import network.matic.maticj.core.protocol.core.methods.response.NetPeerCount;
import network.matic.maticj.core.protocol.core.methods.response.NetVersion;
import network.matic.maticj.core.protocol.core.methods.response.ShhAddToGroup;
import network.matic.maticj.core.protocol.core.methods.response.ShhHasIdentity;
import network.matic.maticj.core.protocol.core.methods.response.ShhMessages;
import network.matic.maticj.core.protocol.core.methods.response.ShhNewFilter;
import network.matic.maticj.core.protocol.core.methods.response.ShhNewGroup;
import network.matic.maticj.core.protocol.core.methods.response.ShhNewIdentity;
import network.matic.maticj.core.protocol.core.methods.response.ShhPost;
import network.matic.maticj.core.protocol.core.methods.response.ShhUninstallFilter;
import network.matic.maticj.core.protocol.core.methods.response.ShhVersion;
import network.matic.maticj.core.protocol.core.methods.response.Web3ClientVersion;
import network.matic.maticj.core.protocol.core.methods.response.Web3Sha3;

import java.math.BigInteger;

/**
 * Core Ethereum JSON-RPC API.
 */
public interface Ethereum {
    Request<?, Web3ClientVersion> web3ClientVersion();

    Request<?, Web3Sha3> web3Sha3(String data);

    Request<?, NetVersion> netVersion();

    Request<?, NetListening> netListening();

    Request<?, NetPeerCount> netPeerCount();

    Request<?, EthProtocolVersion> ethProtocolVersion();

    Request<?, EthCoinbase> ethCoinbase();

    Request<?, EthSyncing> ethSyncing();

    Request<?, EthMining> ethMining();

    Request<?, EthHashrate> ethHashrate();

    Request<?, EthGasPrice> ethGasPrice();

    Request<?, EthAccounts> ethAccounts();

    Request<?, EthBlockNumber> ethBlockNumber();

    Request<?, EthGetBalance> ethGetBalance(
            String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, EthGetStorageAt> ethGetStorageAt(
            String address, BigInteger position,
            DefaultBlockParameter defaultBlockParameter);

    Request<?, EthGetTransactionCount> ethGetTransactionCount(
            String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, EthGetBlockTransactionCountByHash> ethGetBlockTransactionCountByHash(
            String blockHash);

    Request<?, EthGetBlockTransactionCountByNumber> ethGetBlockTransactionCountByNumber(
            DefaultBlockParameter defaultBlockParameter);

    Request<?, EthGetUncleCountByBlockHash> ethGetUncleCountByBlockHash(String blockHash);

    Request<?, EthGetUncleCountByBlockNumber> ethGetUncleCountByBlockNumber(
            DefaultBlockParameter defaultBlockParameter);

    Request<?, EthGetCode> ethGetCode(String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, EthSign> ethSign(String address, String sha3HashOfDataToSign);

    Request<?, EthSendTransaction> ethSendTransaction(
            Transaction transaction);

    Request<?, EthSendTransaction> ethSendRawTransaction(
            String signedTransactionData);

    Request<?, EthCall> ethCall(
            Transaction transaction,
            DefaultBlockParameter defaultBlockParameter);

    Request<?, EthEstimateGas> ethEstimateGas(
            Transaction transaction);

    Request<?, EthBlock> ethGetBlockByHash(String blockHash, boolean returnFullTransactionObjects);

    Request<?, EthBlock> ethGetBlockByNumber(
            DefaultBlockParameter defaultBlockParameter,
            boolean returnFullTransactionObjects);

    Request<?, EthTransaction> ethGetTransactionByHash(String transactionHash);

    Request<?, EthTransaction> ethGetTransactionByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex);

    Request<?, EthTransaction> ethGetTransactionByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex);

    Request<?, EthGetTransactionReceipt> ethGetTransactionReceipt(String transactionHash);

    Request<?, EthBlock> ethGetUncleByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex);

    Request<?, EthBlock> ethGetUncleByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex);

    Request<?, EthGetCompilers> ethGetCompilers();

    Request<?, EthCompileLLL> ethCompileLLL(String sourceCode);

    Request<?, EthCompileSolidity> ethCompileSolidity(String sourceCode);

    Request<?, EthCompileSerpent> ethCompileSerpent(String sourceCode);

    Request<?, EthFilter> ethNewFilter(EthFilter ethFilter);

    Request<?, EthFilter> ethNewBlockFilter();

    Request<?, EthFilter> ethNewPendingTransactionFilter();

    Request<?, EthUninstallFilter> ethUninstallFilter(BigInteger filterId);

    Request<?, EthLog> ethGetFilterChanges(BigInteger filterId);

    Request<?, EthLog> ethGetFilterLogs(BigInteger filterId);

    Request<?, EthLog> ethGetLogs(EthFilter ethFilter);

    Request<?, EthGetWork> ethGetWork();

    Request<?, EthSubmitWork> ethSubmitWork(String nonce, String headerPowHash, String mixDigest);

    Request<?, EthSubmitHashrate> ethSubmitHashrate(String hashrate, String clientId);

    Request<?, DbPutString> dbPutString(String databaseName, String keyName, String stringToStore);

    Request<?, DbGetString> dbGetString(String databaseName, String keyName);

    Request<?, DbPutHex> dbPutHex(String databaseName, String keyName, String dataToStore);

    Request<?, DbGetHex> dbGetHex(String databaseName, String keyName);

    Request<?, ShhPost> shhPost(
            ShhPost shhPost);

    Request<?, ShhVersion> shhVersion();

    Request<?, ShhNewIdentity> shhNewIdentity();

    Request<?, ShhHasIdentity> shhHasIdentity(String identityAddress);

    Request<?, ShhNewGroup> shhNewGroup();

    Request<?, ShhAddToGroup> shhAddToGroup(String identityAddress);

    Request<?, ShhNewFilter> shhNewFilter(ShhFilter shhFilter);

    Request<?, ShhUninstallFilter> shhUninstallFilter(BigInteger filterId);

    Request<?, ShhMessages> shhGetFilterChanges(BigInteger filterId);

    Request<?, ShhMessages> shhGetMessages(BigInteger filterId);
}
