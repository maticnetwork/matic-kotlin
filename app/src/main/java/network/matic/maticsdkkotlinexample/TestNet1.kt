package network.matic.maticsdkkotlinexample

import network.matic.sdk.NetworkConfig

data class TestNet1(
    override var MATIC_PROVIDER: String = ConfigTest.MATIC_PROVIDER,
    override var PARENT_PROVIDER: String = ConfigTest.PARENT_PROVIDER,
    override var ROOT_CONTRACT_ADDRESS: String = ConfigTest.ROOT_CHAIN_ADDRESS,
    override var WITHDRAW_MANAGER_ADDRESS: String = ConfigTest.WITHDRAW_MANAGER_ADDRESS,
    override var DEPOSIT_MANAGER_ADDRESS: String = ConfigTest.DEPOSIT_MANAGER_ADDRESS,
    override var SYNCER_URL: String = ConfigTest.SYNCER_URL,
    override var WATCHER_URL: String = ConfigTest.WATCHER_URL,
    override var MATIC_WETH_ADDRESS: String = ConfigTest.MATIC_WETH_ADDRESS,
    override var ROOT_WETH_ADDRESS: String = ConfigTest.ROOT_WETH_ADDRESS
) : NetworkConfig()