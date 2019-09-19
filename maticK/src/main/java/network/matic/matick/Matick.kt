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
import network.matic.matick.rlp.RlpDecoder
import network.matic.matick.rlp.RlpEncoder
import network.matic.matick.rlp.RlpString
import network.matic.matick.rlp.RlpType
import network.matic.matick.utils.utils.Numeric
import java.math.BigInteger
import java.util.*

class MaticK(private val networkConfig: NetworkConfig, private val credentials: Credentials) {
    private var fromAddress = credentials.address
    private var web3j: Web3j = Web3j.build(HttpService(networkConfig.MATIC_PROVIDER))
    private var web3jParent: Web3j = Web3j.build(HttpService(networkConfig.PARENT_PROVIDER))
    private var syncerUrl: String = networkConfig.SYNCER_URL
    private var watcherUrl: String = networkConfig.WATCHER_URL
    private var rootChainAddress: String = networkConfig.ROOT_CONTRACT_ADDRESS
    private var rootWethAddress: String = networkConfig.ROOT_WETH_ADDRESS
    private var childWethAddress: String = networkConfig.MATIC_WETH_ADDRESS
    private var withdrawManagerAddress: String = networkConfig.WITHDRAW_MANAGER_ADDRESS
    private var depositManagerAddress: String = networkConfig.DEPOSIT_MANAGER_ADDRESS
    private var syncerApiFactory = SyncerApiFactory(networkConfig)
    private var watcherApiFactory = WatcherApiFactory(networkConfig)

    fun loadERC20Contract(contractAddress: String, parent: Boolean): Flowable<ChildERC20> {
        var test = if (parent) networkConfig.PARENT_PROVIDER else networkConfig.MATIC_PROVIDER
        println("test " + contractAddress)
//        return Flowable.just(
//            ChildERC20.load(
//                contractAddress,
//                if (parent) web3jParent else web3j,
//                credentials,
//                CustomContractGasProvider()
//            )
//        )

        return getGasPrice().zipWith(
            estimateGasLimit(),
            BiFunction { gasPrice: EthGasPrice, gasLimit: EthEstimateGas ->
                CustomContractGasProvider(gasPrice.gasPrice, gasLimit.amountUsed)
            }
        ).map {
            ChildERC20.load(
                contractAddress,
                if (parent) web3jParent else web3j,
                credentials,
                CustomContractGasProvider(it.gasPrice, BigInteger.ZERO)
            )
        }
    }

    fun loadERC721Contract(contractAddress: String, parent: Boolean): Flowable<ChildERC721> {
        return Flowable.just(
            ChildERC721.load(
                contractAddress,
                if (parent) web3jParent else web3j,
                credentials,
                CustomContractGasProvider()
            )
        )
//        return getGasPrice().zipWith(
//            estimateGasLimit(),
//            BiFunction { gasPrice: EthGasPrice, gasLimit: EthEstimateGas ->
//                CustomContractGasProvider(gasPrice.gasPrice, gasLimit.amountUsed)
//            }
//        ).map {
//            ChildERC721.load(
//                contractAddress,
//                if (parent) web3jParent else web3j,
//                credentials,
//                CustomContractGasProvider(it.gasPrice, BigInteger.ZERO)
//            )
//        }
    }

    fun loadRootChainContract(contractAddress: String, parent: Boolean): Flowable<RootChain> {

        return getGasPrice().zipWith(
            estimateGasLimit(),
            BiFunction { gasPrice: EthGasPrice, gasLimit: EthEstimateGas ->
                CustomContractGasProvider(gasPrice.gasPrice, gasLimit.amountUsed)
            }
        ).map {
            RootChain.load(
                contractAddress,
                if (parent) web3jParent else web3j,
                credentials,
                CustomContractGasProvider(it.gasPrice, BigInteger.ZERO)
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
                CustomContractGasProvider(gasPrice.gasPrice, gasLimit.amountUsed)
            }
        ).map {
            StandardToken.load(
                contractAddress,
                if (parent) web3jParent else web3j,
                credentials,
                CustomContractGasProvider(it.gasPrice, BigInteger.ZERO)
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
                CustomContractGasProvider(gasPrice.gasPrice, gasLimit.amountUsed)
            }
        ).map {
            DepositManager.load(
                contractAddress,
                if (parent) web3jParent else web3j,
                credentials,
                CustomContractGasProvider(it.gasPrice, BigInteger.ZERO)
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
                CustomContractGasProvider(gasPrice.gasPrice, gasLimit.amountUsed)
            }
        ).map {
            WithdrawManager.load(
                contractAddress,
                if (parent) web3jParent else web3j,
                credentials,
                CustomContractGasProvider(it.gasPrice, BigInteger.ZERO)
            )
        }
    }

    fun getEtherBalance() = web3jParent.ethGetBalance(
        fromAddress,
        DefaultBlockParameterName.LATEST
    )

    fun getGasPrice() = web3jParent.ethGasPrice().flowable()


    fun estimateGasLimit(data: String = "0x") = web3j.ethEstimateGas(
        Transaction.createEthCallTransaction(
            fromAddress,
            fromAddress,
            data
        )
    ).flowable()

    fun getNonce() = web3j.ethGetTransactionCount(
        fromAddress, DefaultBlockParameterName.LATEST
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
                it.approve(rootChainAddress, amount)
            }
            .flatMap {
                val signedTransaction = TransactionEncoder.signMessage(
                    it, credentials
                )
                val hexValue = Numeric.toHexString(signedTransaction)
                web3jParent.ethSendRawTransaction(hexValue).flowable()
            }
    }

    fun depositERC20Tokens(
        contractAddress: String,
        amount: BigInteger,
        parent: Boolean = true
    ): Flowable<EthSendTransaction> {
        return loadRootChainContract(contractAddress, parent)
            .flatMapSingle {
                it.deposit(contractAddress, fromAddress, amount)
            }.flatMap {
                val signedTransaction = TransactionEncoder.signMessage(
                    it, credentials
                )
                val hexValue = Numeric.toHexString(signedTransaction)
                web3jParent.ethSendRawTransaction(hexValue).flowable()
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
                    fromAddress,
                    rootChainAddress,
                    amount
                )
            }.flatMap {
                val signedTransaction = TransactionEncoder.signMessage(
                    it, credentials
                )
                val hexValue = Numeric.toHexString(signedTransaction)
                web3jParent.ethSendRawTransaction(hexValue).flowable()
            }
    }

    fun approveERC721TokenForDeposit(
        contractAddress: String,
        amount: BigInteger,
        parent: Boolean = true
    ): Flowable<EthSendTransaction> {
        return loadERC721Contract(contractAddress, parent)
            .flatMapSingle {
                it.approve(rootChainAddress, amount)
            }.flatMap {
                val signedTransaction = TransactionEncoder.signMessage(
                    it, credentials
                )
                val hexValue = Numeric.toHexString(signedTransaction)
                web3jParent.ethSendRawTransaction(hexValue).flowable()
            }
    }

    fun depositERC721Tokens(
        contractAddress: String,
        amount: BigInteger,
        parent: Boolean = true
    ): Flowable<EthSendTransaction> {
        return loadRootChainContract(contractAddress, parent)
            .flatMapSingle {
                it.depositERC721(contractAddress, fromAddress, amount)
            }.flatMap {
                val signedTransaction = TransactionEncoder.signMessage(
                    it, credentials
                )
                val hexValue = Numeric.toHexString(signedTransaction)
                web3jParent.ethSendRawTransaction(hexValue).flowable()
            }
    }

    fun transferTokens(
        recipientAddress: String,
        contractAddress: String,
        amount: BigInteger,
        parent: Boolean = false
    ): Flowable<EthSendTransaction> {
        with(if (parent) web3jParent else web3j) {
            return loadERC20Contract(contractAddress, parent)
                .flatMapSingle {
                    it.transfer(recipientAddress, amount)
                }.flatMap {
                    val signedTransaction = TransactionEncoder.signMessage(
                        it, credentials
                    )
                    println(signedTransaction.size)
                    val hexValue = Numeric.toHexString(signedTransaction)
                    println(hexValue)
                    ethSendRawTransaction(hexValue).flowable()
                }
        }
    }

    fun transferERC721Tokens(
        recipientAddress: String,
        contractAddress: String,
        amount: BigInteger,
        parent: Boolean = false
    ): Flowable<EthSendTransaction> {
        with(if (parent) web3jParent else web3j) {
            return loadERC721Contract(contractAddress, parent).flatMapSingle {
                it.transferFrom(fromAddress, recipientAddress, amount)
            }.flatMap {
                val signedTransaction = TransactionEncoder.signMessage(
                    it, credentials
                )
                val hexValue = Numeric.toHexString(signedTransaction)
                ethSendRawTransaction(hexValue).flowable()
            }
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
            .flatMap {
                val signedTransaction = TransactionEncoder.signMessage(
                    it, credentials
                )
                val hexValue = Numeric.toHexString(signedTransaction)
                web3j.ethSendRawTransaction(hexValue).flowable()
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
            }.flatMap {
                val signedTransaction = TransactionEncoder.signMessage(
                    it, credentials
                )
                val hexValue = Numeric.toHexString(signedTransaction)
                web3j.ethSendRawTransaction(hexValue).flowable()
            }
    }

    fun getTx(txHash: String): Single<TransactionModel> {
        return syncerApiFactory.getRetrofitInstance().getTransaction(txHash)
            .subscribeOn(Schedulers.io())
    }

    fun getReceipt(txHash: String): Single<TransactionModel> {
        return syncerApiFactory.getRetrofitInstance().getTransactionReceipt(txHash)
            .subscribeOn(Schedulers.io())
    }

    fun getTxProof(txHash: String): Single<TxProofModel> {
        return syncerApiFactory.getRetrofitInstance().getTxProof(txHash)
            .subscribeOn(Schedulers.io())
    }

//    fun verifyTxProof(txHash: String) {
//        SyncerApiFactory.instance.verifyTxProof(txHash)
//        .subscribeOn(Schedulers.io())
//    }


    fun getReceiptProof(txHash: String): Single<TxProofModel> {
        return syncerApiFactory.getRetrofitInstance().getReceiptProof(txHash)
            .subscribeOn(Schedulers.io())
    }

    //
//    fun verifyReceiptProof(txHash: String) {
//
//    }
//
    fun getHeaderObject(blockNumber: String): Single<WatcherModel> {
        return watcherApiFactory.getRetrofitInstance().getHeaderObject(blockNumber)
            .subscribeOn(Schedulers.io())
    }

    fun getHeaderProof(blockNumber: String, start: String, end: String): Single<Header> {
        return syncerApiFactory.getRetrofitInstance().getHeaderProof(blockNumber, start, end)
            .subscribeOn(Schedulers.io())
    }

    fun verifyHeaderProof() {

    }

    fun withdraw(
        contractAddress: String,
        txHash: String,
        parent: Boolean = true
    ): Flowable<EthSendTransaction> {
        val txProof = getTxProof(txHash)
        val receiptProof = getReceiptProof(txHash)
        val header = getHeaderObject(txProof.blockingGet().proof.blockNumber)

        val headerProof = getHeaderProof(
            txProof.blockingGet().proof.blockNumber,
            header.blockingGet().start,
            header.blockingGet().end
        )

        val trimmedProof = headerProof.blockingGet().proof.proof.joinToString("").replace("0x", "")
        val sPath = receiptProof.blockingGet().proof.path
        val t = Numeric.hexStringToByteArray(sPath)
        val values = ArrayList<RlpType>()
        values.add(RlpString.create(sPath))

        // TODO: Offset is hardcoded here, not good approach need to think on this
//        val rlpList = RlpList(values)
//        val encoderRlp = RlpEncoder.encode(rlpList)

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
        }.flatMap {
            val signedTransaction = TransactionEncoder.signMessage(
                it, credentials
            )
            val hexValue = Numeric.toHexString(signedTransaction)
            web3jParent.ethSendRawTransaction(hexValue).flowable()
        }
    }

    fun processExits(contractAddress: String, parent: Boolean = true) {
        /**
         * TODO
         */
    }

    /*fun setWallet(privateKey: String) {
        credentials = Credentials.create(privateKey)
        fromAddress = credentials.address
    }*/

}

internal class CustomContractGasProvider(
    private val ethGasPrice: BigInteger = BigInteger.ZERO,
    private val ethGasLimit: BigInteger = BigInteger.ZERO
) : ContractGasProvider {

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