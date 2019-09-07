package network.matic.matick

import network.matic.matick.core.protocol.Web3j
import network.matic.matick.core.protocol.core.DefaultBlockParameterName
import network.matic.matick.core.protocol.core.methods.request.Transaction
import network.matic.matick.core.protocol.http.HttpService


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

class Matick {
    private var web3j: Web3j
    private var web3jParent: Web3j
    private var syncerUrl : String
    private var watcherUrl : String
    private var rootChainAddress : String
    private var rootWethAddress : String
    private var childWethAddress : String
    private var withdrawManagerAddress : String
    private var depositManagerAddress : String

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


    fun contract() : String {
        println("welcome to contract")
        return("welcome sayla")
    }

    fun getEtherBalance() = web3j.ethGetBalance(
    ConfigUtils.FROM_ADDRESS,
    DefaultBlockParameterName.LATEST
    )
    .flowable()

    fun getERC20Balnce() {

    }

    fun getGasPrice() = web3j.ethGasPrice().flowable()

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

    fun apiCall() {

    }
}