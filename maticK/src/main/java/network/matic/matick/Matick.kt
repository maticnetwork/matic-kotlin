package network.matic.matick

import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import network.matic.matick.artifacts.*
import network.matic.matick.core.protocol.Web3j
import network.matic.matick.core.protocol.core.DefaultBlockParameterName
import network.matic.matick.core.protocol.core.methods.request.Transaction
import network.matic.matick.core.protocol.core.methods.response.EthEstimateGas
import network.matic.matick.core.protocol.core.methods.response.EthGasPrice
import network.matic.matick.core.protocol.core.methods.response.TransactionReceipt
import network.matic.matick.core.protocol.http.HttpService
import network.matic.matick.core.tx.gas.ContractGasProvider
import network.matic.matick.crypto.Credentials
import java.math.BigInteger


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


    fun contract(): String {
        println("welcome to contract")
        return ("welcome sayla")
    }

    fun loadERC20Contract(contractAddress: String, parent: Boolean): Flowable<ChildERC20> {

        return getGasPrice().zipWith(
            estimateGasLimit(),
            BiFunction { gasPrice: EthGasPrice, gasLimit: EthEstimateGas ->
                GasObject(gasPrice.gasPrice, gasLimit.amountUsed)
            }
        ).map {
            println("gas ${it.ethEstimateGas}")
            ChildERC20.load(
                contractAddress,
                if (parent) web3jParent else web3j,
                Credentials.create(ConfigUtils.PRIVATE_KEY),
                CustomContractGasProvider(it.ethGasPrice, BigInteger.valueOf(67000))
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
            println("gas ${it.ethEstimateGas}")
            ChildERC721.load(
                contractAddress,
                if (parent) web3jParent else web3j,
                Credentials.create(ConfigUtils.PRIVATE_KEY),
                CustomContractGasProvider(it.ethGasPrice, BigInteger.valueOf(67000))
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
            println("gas ${it.ethEstimateGas}")
            RootChain.load(
                contractAddress,
                if (parent) web3jParent else web3j,
                Credentials.create(ConfigUtils.PRIVATE_KEY),
                CustomContractGasProvider(it.ethGasPrice, BigInteger.valueOf(67000))
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
            println("gas ${it.ethEstimateGas}")
            StandardToken.load(
                contractAddress,
                if (parent) web3jParent else web3j,
                Credentials.create(ConfigUtils.PRIVATE_KEY),
                CustomContractGasProvider(it.ethGasPrice, BigInteger.valueOf(67000))
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
            println("gas ${it.ethEstimateGas}")
            DepositManager.load(
                contractAddress,
                if (parent) web3jParent else web3j,
                Credentials.create(ConfigUtils.PRIVATE_KEY),
                CustomContractGasProvider(it.ethGasPrice, BigInteger.valueOf(67000))
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
            println("gas ${it.ethEstimateGas}")
            WithdrawManager.load(
                contractAddress,
                if (parent) web3jParent else web3j,
                Credentials.create(ConfigUtils.PRIVATE_KEY),
                CustomContractGasProvider(it.ethGasPrice, BigInteger.valueOf(67000))
            )
        }
    }

    fun getEtherBalance() = web3j.ethGetBalance(
        ConfigUtils.FROM_ADDRESS,
        DefaultBlockParameterName.LATEST
    )

    fun getGasPrice() = web3jParent.ethGasPrice().flowable()


    fun estimateGasLimit() = web3j.ethEstimateGas(
        Transaction.createEthCallTransaction(
            ConfigUtils.FROM_ADDRESS,
            ConfigUtils.recipientAddress,
            "0xAb61728dbcB2ceb2BEC355472fA17A15A661125D"
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
//                it.balanceOf(userAddress).flowable()
//            }
    }

    fun approveERC20TokensForDeposit(
        contractAddress: String,
        amount: BigInteger,
        parent: Boolean = true
    ): Flowable<TransactionReceipt> {
        return loadStandardTokenContract(contractAddress, parent)
            .flatMap {
                it.approve(ConfigUtils.ROOTCHAIN_ADDRESS, amount).flowable()
            }
    }

    fun depositERC20Tokens(
        contractAddress: String,
        amount: BigInteger,
        parent: Boolean = true
    ): Flowable<TransactionReceipt> {
        return loadRootChainContract(contractAddress, parent)
            .flatMap {
                it.deposit(contractAddress, ConfigUtils.FROM_ADDRESS, amount).flowable()
            }
    }

    fun safeDepositERC721Tokens(
        contractAddress: String,
        amount: BigInteger,
        parent: Boolean = true
    ): Flowable<TransactionReceipt> {
        return loadERC721Contract(contractAddress, parent)
            .flatMap {
                it.safeTransferFrom(ConfigUtils.FROM_ADDRESS, ConfigUtils.ROOTCHAIN_ADDRESS, amount)
                    .flowable()
            }
    }

    fun approveERC721TokenForDeposit(
        contractAddress: String,
        amount: BigInteger,
        parent: Boolean = true
    ): Flowable<TransactionReceipt> {
        return loadERC721Contract(contractAddress, parent)
            .flatMap {
                it.approve(ConfigUtils.ROOTCHAIN_ADDRESS, amount).flowable()
            }
    }

    fun depositERC721Tokens(
        contractAddress: String,
        amount: BigInteger,
        parent: Boolean = true
    ): Flowable<TransactionReceipt> {
        return loadRootChainContract(contractAddress, parent)
            .flatMap {
                it.depositERC721(contractAddress, ConfigUtils.FROM_ADDRESS, amount).flowable()
            }
    }

    fun transferTokens(
        receipentAddress: String,
        contractAddress: String,
        amount: BigInteger,
        parent: Boolean = false
    ): Flowable<TransactionReceipt> {
        return loadERC20Contract(contractAddress, parent)
            .flatMap {
                it.transfer(receipentAddress, amount).flowable()
            }
//            .map {

//                val signedTransaction = TransactionEncoder.signMessage(it, Credentials.create(
//                    ConfigUtils.privateKey
//                ))
////                val hexValue = Numeric.toHexString(signedTransaction)
////                web3j.ethSendRawTransaction(hexValue).send()
//            }
    }

    fun transferERC721Tokens(
        receipentAddress: String,
        contractAddress: String,
        amount: BigInteger,
        parent: Boolean = false
    ): Flowable<TransactionReceipt> {
        return loadERC721Contract(contractAddress, parent).flatMap {
            it.transferFrom(ConfigUtils.FROM_ADDRESS, receipentAddress, amount).flowable()
        }
    }

    fun transferEthers(contractAddress: String, parent: Boolean = false) {

    }

    fun startWithdraw(
        contractAddress: String,
        amount: BigInteger,
        parent: Boolean = false
    ): Flowable<TransactionReceipt> {
        return loadERC20Contract(contractAddress, parent)
            .flatMap {
                it.withdraw(amount).flowable()
            }
//            .map {
//                val signedTransaction = TransactionEncoder.signMessage(it, Credentials.create(
//                    ConfigUtils.privateKey
//                ))
//                val hexValue = Numeric.toHexString(signedTransaction)
//                web3j.ethSendRawTransaction(hexValue).send()
//            }
    }

    fun startERC721Withdraw(
        contractAddress: String,
        amount: BigInteger,
        parent: Boolean = false
    ): Flowable<TransactionReceipt> {
        return loadERC721Contract(contractAddress, parent)
            .flatMap {
                it.withdraw(amount).flowable()
            }
    }

    fun getTx() {

    }

    fun getReceipt() {

    }

    fun getTxProof() {

    }

    fun verifyTxProof() {

    }

    fun getReceiptProof() {

    }

    fun verifyReceiptProof() {

    }

    fun getHeaderObject() {

    }

    fun getHeaderProof() {

    }

    fun verifyHeaderProof() {

    }

    fun withdraw(contractAddress: String, parent: Boolean = true) {

    }

    fun processExits(contractAddress: String, parent: Boolean = true) {

    }

}

internal class CustomContractGasProvider(val ethGasPrice: BigInteger, val ethGasLimit: BigInteger) :
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