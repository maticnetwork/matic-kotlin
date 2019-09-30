package network.matic.maticsdkkotlinexample

import network.matic.sdk.NetworkConfig

data class TestNet1(
  override var MATIC_PROVIDER: String = ConfigExample.MATIC_PROVIDER,
  override var PARENT_PROVIDER: String = ConfigExample.PARENT_PROVIDER,
  override var ROOT_CONTRACT_ADDRESS: String = ConfigExample.ROOT_CHAIN_ADDRESS,
  override var WITHDRAW_MANAGER_ADDRESS: String = ConfigExample.WITHDRAW_MANAGER_ADDRESS,
  override var DEPOSIT_MANAGER_ADDRESS: String = ConfigExample.DEPOSIT_MANAGER_ADDRESS,
  override var SYNCER_URL: String = ConfigExample.SYNCER_URL,
  override var WATCHER_URL: String = ConfigExample.WATCHER_URL,
  override var MATIC_WETH_ADDRESS: String = ConfigExample.MATIC_WETH_ADDRESS,
  override var ROOT_WETH_ADDRESS: String = ConfigExample.ROOT_WETH_ADDRESS
) : NetworkConfig()