package network.matic.matick

import network.matic.matick.core.protocol.Web3j
import network.matic.matick.core.protocol.http.HttpService

class Maticj {
    private lateinit var web3j: Web3j

    constructor() {
        web3j = Web3j.build(HttpService("http://10.0.2.2:7545"))

    }

    fun getEtherBalance() {
    }

    fun getERC20Balnce() {

    }

    fun contract() {

    }
}