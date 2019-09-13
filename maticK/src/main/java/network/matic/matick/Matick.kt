package network.matic.matick

import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import network.matic.matick.api.SyncerApiFactory
import network.matic.matick.api.WatcherApiFactory
import network.matic.matick.artifacts.*
import network.matic.matick.core.protocol.Web3j
import network.matic.matick.core.protocol.core.DefaultBlockParameterName
import network.matic.matick.core.protocol.core.methods.request.Transaction
import network.matic.matick.core.protocol.core.methods.response.EthEstimateGas
import network.matic.matick.core.protocol.core.methods.response.EthGasPrice
import network.matic.matick.core.protocol.core.methods.response.EthSendTransaction
import network.matic.matick.core.protocol.http.HttpService
import network.matic.matick.core.tx.gas.ContractGasProvider
import network.matic.matick.crypto.Credentials
import network.matic.matick.crypto.TransactionEncoder
import network.matic.matick.model.Header
import network.matic.matick.model.TransactionModel
import network.matic.matick.model.TxProofModel
import network.matic.matick.model.WatcherModel
import network.matic.matick.rlp.*
import network.matic.matick.utils.utils.Numeric
import java.math.BigInteger
import java.util.*


private val hexArray = "0123456789ABCDEF".toCharArray()


//internal class ConfigUtils(
//    var web3j: Web3j,
//    var web3jParent: Web3j,
//    var syncerUrl : String,
//    var watcherUrl : String,
//    var rootChainAddress : String,
//    var rootWethAddress : String,
//    var childWethAddress : String,
//    var withdrawManagerAddress : String,
//    var depositManagerAddress : String
//)


internal class GasObject(
    val ethGasPrice: BigInteger,
    val ethEstimateGas: BigInteger
)

class Matick {
    private var web3j: Web3j
    private var web3jParent: Web3j
    private var syncerUrl: String
    private var watcherUrl: String
    private var rootChainAddress: String
    private var rootWethAddress: String
    private var childWethAddress: String
    private var withdrawManagerAddress: String
    private var depositManagerAddress: String


    constructor() {
        web3j = Web3j.build(HttpService(ConfigUtils.MATIC_PROVIDER))
        web3jParent = Web3j.build(HttpService(ConfigUtils.PARENT_PROVIDER))
        syncerUrl = ConfigUtils.SYNCER_URL
        watcherUrl = ConfigUtils.WATCHER_URL
        rootChainAddress = ConfigUtils.ROOTCHAIN_ADDRESS
        rootWethAddress = ConfigUtils.ROOTWETH_ADDRESS
        childWethAddress = ConfigUtils.MATICWETH_ADDRESS
        withdrawManagerAddress = ConfigUtils.WITHDRAWMANAGER_ADDRESS
        depositManagerAddress = ConfigUtils.DEPOSITMANAGER_ADDRESS
    }

    fun loadERC20Contract(contractAddress: String, parent: Boolean): Flowable<ChildERC20> {

        return getGasPrice().zipWith(
            estimateGasLimit(),
            BiFunction { gasPrice: EthGasPrice, gasLimit: EthEstimateGas ->
                GasObject(gasPrice.gasPrice, gasLimit.amountUsed)
            }
        ).map {
            ChildERC20.load(
                contractAddress,
                if (parent) web3jParent else web3j,
                Credentials.create(ConfigUtils.PRIVATE_KEY),
                CustomContractGasProvider(it.ethGasPrice, BigInteger.ZERO)
            )
        }
    }

    fun loadERC721Contract(contractAddress: String, parent: Boolean): Flowable<ChildERC721> {

        return getGasPrice().zipWith(
            estimateGasLimit(),
            BiFunction { gasPrice: EthGasPrice, gasLimit: EthEstimateGas ->
                GasObject(gasPrice.gasPrice, gasLimit.amountUsed)
            }
        ).map {
            ChildERC721.load(
                contractAddress,
                if (parent) web3jParent else web3j,
                Credentials.create(ConfigUtils.PRIVATE_KEY),
                CustomContractGasProvider(it.ethGasPrice, BigInteger.ZERO)
            )
        }
    }

    fun loadRootChainContract(contractAddress: String, parent: Boolean): Flowable<RootChain> {

        return getGasPrice().zipWith(
            estimateGasLimit(),
            BiFunction { gasPrice: EthGasPrice, gasLimit: EthEstimateGas ->
                GasObject(gasPrice.gasPrice, gasLimit.amountUsed)
            }
        ).map {
            RootChain.load(
                contractAddress,
                if (parent) web3jParent else web3j,
                Credentials.create(ConfigUtils.PRIVATE_KEY),
                CustomContractGasProvider(it.ethGasPrice, BigInteger.ZERO)
            )
        }
    }

    fun loadStandardTokenContract(
        contractAddress: String,
        parent: Boolean
    ): Flowable<StandardToken> {

        return getGasPrice().zipWith(
            estimateGasLimit(),
            BiFunction { gasPrice: EthGasPrice, gasLimit: EthEstimateGas ->
                GasObject(gasPrice.gasPrice, gasLimit.amountUsed)
            }
        ).map {
            StandardToken.load(
                contractAddress,
                if (parent) web3jParent else web3j,
                Credentials.create(ConfigUtils.PRIVATE_KEY),
                CustomContractGasProvider(it.ethGasPrice, BigInteger.ZERO)
            )
        }
    }

    fun loadDepositMangerContract(
        contractAddress: String,
        parent: Boolean
    ): Flowable<DepositManager> {

        return getGasPrice().zipWith(
            estimateGasLimit(),
            BiFunction { gasPrice: EthGasPrice, gasLimit: EthEstimateGas ->
                GasObject(gasPrice.gasPrice, gasLimit.amountUsed)
            }
        ).map {
            DepositManager.load(
                contractAddress,
                if (parent) web3jParent else web3j,
                Credentials.create(ConfigUtils.PRIVATE_KEY),
                CustomContractGasProvider(it.ethGasPrice, BigInteger.ZERO)
            )
        }
    }

    fun loadWithdrawMangerContract(
        contractAddress: String,
        parent: Boolean
    ): Flowable<WithdrawManager> {

        return getGasPrice().zipWith(
            estimateGasLimit(),
            BiFunction { gasPrice: EthGasPrice, gasLimit: EthEstimateGas ->
                GasObject(gasPrice.gasPrice, gasLimit.amountUsed)
            }
        ).map {
            WithdrawManager.load(
                contractAddress,
                if (parent) web3jParent else web3j,
                Credentials.create(ConfigUtils.PRIVATE_KEY),
                CustomContractGasProvider(it.ethGasPrice, BigInteger.ZERO)
            )
        }
    }

    fun getEtherBalance() = web3j.ethGetBalance(
        ConfigUtils.FROM_ADDRESS,
        DefaultBlockParameterName.LATEST
    )

    fun getGasPrice() = web3jParent.ethGasPrice().flowable()


    fun estimateGasLimit(data: String = "0x") = web3j.ethEstimateGas(
        Transaction.createEthCallTransaction(
            ConfigUtils.FROM_ADDRESS,
            ConfigUtils.recipientAddress,
            data
        )
    ).flowable()

    fun getNonce() = web3j.ethGetTransactionCount(
        ConfigUtils.FROM_ADDRESS, DefaultBlockParameterName.LATEST
    )

    fun getERC20Balance(
        contractAddress: String,
        userAddress: String,
        parent: Boolean = false
    ): Flowable<BigInteger> {
        return loadERC20Contract(contractAddress, parent)
            .flatMap {
                it.balanceOf(userAddress).flowable()
            }
    }

    fun getERC721Balance(
        contractAddress: String,
        userAddress: String,
        parent: Boolean = false
    ): Flowable<BigInteger> {
        return loadERC721Contract(contractAddress, parent)
            .flatMap {
                it.balanceOf(userAddress).flowable()
            }
    }

    fun getMappedcontractAddress(contractAddress: String, parent: Boolean = false) {

    }

    fun apiCall() {

    }

    // Hold this, need to check how to pass extra params to e.g need to pass amount as value
    fun depositEthers(contractAddress: String, userAddress: String, parent: Boolean = false) {
//        loadRootChainContract(contractAddress, parent)
//            .flatMap {
//                it.depositEthers()
//            }
    }

    fun approveERC20TokensForDeposit(
        contractAddress: String,
        amount: BigInteger,
        parent: Boolean = true
    ): Flowable<EthSendTransaction> {
        return loadStandardTokenContract(contractAddress, parent)
            .flatMapSingle {
                it.approve(ConfigUtils.ROOTCHAIN_ADDRESS, amount)
            }
            .map {
                val signedTransaction = TransactionEncoder.signMessage(
                    it, Credentials.create(
                        ConfigUtils.privateKey
                    )
                )
                val hexValue = Numeric.toHexString(signedTransaction)
                web3j.ethSendRawTransaction(hexValue).send()
            }
    }

    fun depositERC20Tokens(
        contractAddress: String,
        amount: BigInteger,
        parent: Boolean = true
    ): Flowable<EthSendTransaction> {
        return loadRootChainContract(contractAddress, parent)
            .flatMapSingle {
                it.deposit(contractAddress, ConfigUtils.FROM_ADDRESS, amount)
            }.map {
                val signedTransaction = TransactionEncoder.signMessage(
                    it, Credentials.create(
                        ConfigUtils.privateKey
                    )
                )
                val hexValue = Numeric.toHexString(signedTransaction)
                web3j.ethSendRawTransaction(hexValue).send()
            }
    }

    fun safeDepositERC721Tokens(
        contractAddress: String,
        amount: BigInteger,
        parent: Boolean = true
    ): Flowable<EthSendTransaction> {
        return loadERC721Contract(contractAddress, parent)
            .flatMapSingle {
                it.safeTransferFrom(
                    ConfigUtils.FROM_ADDRESS,
                    ConfigUtils.ROOTCHAIN_ADDRESS,
                    amount
                )
            }.map {
                val signedTransaction = TransactionEncoder.signMessage(
                    it, Credentials.create(
                        ConfigUtils.privateKey
                    )
                )
                val hexValue = Numeric.toHexString(signedTransaction)
                web3j.ethSendRawTransaction(hexValue).send()
            }
    }

    fun approveERC721TokenForDeposit(
        contractAddress: String,
        amount: BigInteger,
        parent: Boolean = true
    ): Flowable<EthSendTransaction> {
        return loadERC721Contract(contractAddress, parent)
            .flatMapSingle {
                it.approve(ConfigUtils.ROOTCHAIN_ADDRESS, amount)
            }.map {
                val signedTransaction = TransactionEncoder.signMessage(
                    it, Credentials.create(
                        ConfigUtils.privateKey
                    )
                )
                val hexValue = Numeric.toHexString(signedTransaction)
                web3j.ethSendRawTransaction(hexValue).send()
            }
    }

    fun depositERC721Tokens(
        contractAddress: String,
        amount: BigInteger,
        parent: Boolean = true
    ): Flowable<EthSendTransaction> {
        return loadRootChainContract(contractAddress, parent)
            .flatMapSingle {
                it.depositERC721(contractAddress, ConfigUtils.FROM_ADDRESS, amount)
            }.map {
                val signedTransaction = TransactionEncoder.signMessage(
                    it, Credentials.create(
                        ConfigUtils.privateKey
                    )
                )
                val hexValue = Numeric.toHexString(signedTransaction)
                web3j.ethSendRawTransaction(hexValue).send()
            }
    }

    fun transferTokens(
        receipentAddress: String,
        contractAddress: String,
        amount: BigInteger,
        parent: Boolean = false
    ): Flowable<EthSendTransaction> {
        return loadERC20Contract(contractAddress, parent)
            .flatMapSingle {
                it.transfer(receipentAddress, amount)
            }.map {
                val signedTransaction = TransactionEncoder.signMessage(
                    it, Credentials.create(
                        ConfigUtils.privateKey
                    )
                )
                val hexValue = Numeric.toHexString(signedTransaction)
                web3j.ethSendRawTransaction(hexValue).send()
            }
    }

    fun transferERC721Tokens(
        receipentAddress: String,
        contractAddress: String,
        amount: BigInteger,
        parent: Boolean = false
    ): Flowable<EthSendTransaction> {
        return loadERC721Contract(contractAddress, parent).flatMapSingle {
            it.transferFrom(ConfigUtils.FROM_ADDRESS, receipentAddress, amount)
        }.map {
            val signedTransaction = TransactionEncoder.signMessage(
                it, Credentials.create(
                    ConfigUtils.privateKey
                )
            )
            val hexValue = Numeric.toHexString(signedTransaction)
            web3j.ethSendRawTransaction(hexValue).send()
        }
    }

    fun transferEthers(contractAddress: String, parent: Boolean = false) {

    }

    fun startWithdraw(
        contractAddress: String,
        amount: BigInteger,
        parent: Boolean = false
    ): Flowable<EthSendTransaction> {
        return loadERC20Contract(contractAddress, parent)
            .flatMapSingle {
                it.withdraw(amount)
            }
            .map {
                val signedTransaction = TransactionEncoder.signMessage(
                    it, Credentials.create(
                        ConfigUtils.privateKey
                    )
                )
                val hexValue = Numeric.toHexString(signedTransaction)
                web3j.ethSendRawTransaction(hexValue).send()
            }
    }

    fun startERC721Withdraw(
        contractAddress: String,
        amount: BigInteger,
        parent: Boolean = false
    ): Flowable<EthSendTransaction> {
        return loadERC721Contract(contractAddress, parent)
            .flatMapSingle {
                it.withdraw(amount)
            }.map {
                val signedTransaction = TransactionEncoder.signMessage(
                    it, Credentials.create(
                        ConfigUtils.privateKey
                    )
                )
                val hexValue = Numeric.toHexString(signedTransaction)
                web3j.ethSendRawTransaction(hexValue).send()
            }
    }

    fun getTx(txHash: String): Single<TransactionModel> {
        return SyncerApiFactory.getRetrofitInstance().getTransaction(txHash)
            .subscribeOn(Schedulers.io())

    }

    fun getReceipt(txHash: String): Single<TransactionModel> {
        return SyncerApiFactory.getRetrofitInstance().getTransactionReceipt(txHash)
            .subscribeOn(Schedulers.io())

    }

    fun getTxProof(txHash: String): Single<TxProofModel> {
        return SyncerApiFactory.getRetrofitInstance().getTxProof(txHash)
            .subscribeOn(Schedulers.io())
    }

//    fun verifyTxProof(txHash: String) {
//        SyncerApiFactory.instance.verifyTxProof(txHash)
//        .subscribeOn(Schedulers.io())
//    }


    fun getReceiptProof(txHash: String): Single<TxProofModel> {
        return SyncerApiFactory.getRetrofitInstance().getReceiptProof(txHash)
            .subscribeOn(Schedulers.io())
    }

    //
//    fun verifyReceiptProof(txHash: String) {
//
//    }
//
    fun getHeaderObject(blockNumber: String): Single<WatcherModel> {
        return WatcherApiFactory.getRetrofitInstance().getHeaderObject(blockNumber)
            .subscribeOn(Schedulers.io())
    }

    fun getHeaderProof(blockNumber: String, start: String, end: String): Single<Header> {
        return SyncerApiFactory.getRetrofitInstance().getHeaderProof(blockNumber, start, end)
            .subscribeOn(Schedulers.io())
    }

    fun verifyHeaderProof() {

    }

    fun withdraw(
        contractAddress: String,
        txHash: String,
        parent: Boolean = true
    ): Flowable<EthSendTransaction> {
        var txProof = getTxProof(txHash)
        var receiptProof = getReceiptProof(txHash)
        var header = getHeaderObject(txProof.blockingGet().proof.blockNumber)

        var headerProof = getHeaderProof(
            txProof.blockingGet().proof.blockNumber,
            header.blockingGet().start,
            header.blockingGet().end
        )

        var trimmedProof = headerProof.blockingGet().proof.proof.joinToString("").replace("0x", "")
        val sPath = receiptProof.blockingGet().proof.path
        var t = Numeric.hexStringToByteArray(sPath)
        val values = ArrayList<RlpType>()
        values.add(RlpString.create(sPath))

        val rlpList = RlpList(values)
//        val encoderRlp = RlpEncoder.encode(rlpList)


        // TODO: Offset is harcoded here, not good approch need to think on this
        val encoderRlp = RlpEncoder.encode(t, RlpDecoder.OFFSET_SHORT_STRING)

        val hexValue = Numeric.toHexString(encoderRlp)


        return loadWithdrawMangerContract(contractAddress, parent).flatMapSingle {
            it.withdrawBurntTokens(
                BigInteger.valueOf(header.blockingGet().number.toLong()),
                Numeric.hexStringToByteArray(trimmedProof),
                BigInteger.valueOf(txProof.blockingGet().proof.blockNumber.toLong()),
                BigInteger.valueOf(txProof.blockingGet().proof.blockTimestamp.toLong()),
                Numeric.hexStringToByteArray(txProof.blockingGet().proof.root),
                Numeric.hexStringToByteArray(receiptProof.blockingGet().proof.root),
                Numeric.hexStringToByteArray(hexValue),
                Numeric.hexStringToByteArray(txProof.blockingGet().proof.value),
                Numeric.hexStringToByteArray(txProof.blockingGet().proof.parentNodes),
                Numeric.hexStringToByteArray(receiptProof.blockingGet().proof.value),
                Numeric.hexStringToByteArray(receiptProof.blockingGet().proof.parentNodes)
            )
        }.map {
            val signedTransaction = TransactionEncoder.signMessage(
                it, Credentials.create(
                    ConfigUtils.privateKey
                )
            )
            val hexValue = Numeric.toHexString(signedTransaction)
            web3j.ethSendRawTransaction(hexValue).send()
        }
    }

    fun processExits(contractAddress: String, parent: Boolean = true) {

    }

}

internal class CustomContractGasProvider(
    val ethGasPrice: BigInteger,
    val ethGasLimit: BigInteger
) :
    ContractGasProvider {

    override fun getGasLimit(contractFunc: String?): BigInteger {
        return ethGasLimit
    }

    override fun getGasLimit(): BigInteger {
        return ethGasLimit
    }

    override fun getGasPrice(contractFunc: String?): BigInteger {
        return ethGasPrice
    }

    override fun getGasPrice(): BigInteger {
        return ethGasPrice
    }

}