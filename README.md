# Matic Kotlin SDK
[![](https://jitpack.io/v/maticnetwork/matic-kotlin.svg)](https://jitpack.io/#maticnetwork/matic-kotlin)


This repository contains the `matic` client library. `matic` makes it easy for developers, who may not be deeply familiar with smart contract development, to interact with the various components of Matic Network.

This library will help developers to move assets from Ethereum chain to Matic chain, and withdraw from Matic to Ethereum using fraud proofs.

We will be improving this library to make all features available like Plasma Faster Exit, challenge exit, finalize exit and more.

## Installation

  Add Jitpack to your project level build.gradle file
  
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  Add the dependency in your app module's build.gradle file

	dependencies {
	        implementation 'com.github.maticnetwork:matic-kotlin:Tag'
	}

## Getting Started

 ```java
 // Import Matic SDK
 import network.matic.sdk.Matic
   
// Setup Config Object by extending NetworkConfig
data class TestNet1(
  // Set Matic provider - string or provider instance
  // Example: 'https://testnet.matic.network'
  override var MATIC_PROVIDER: String = <rpc-url>,
  
  // Set Mainchain provider - string or provider instance
  // Example: 'https://ropsten.infura.io'
  override var PARENT_PROVIDER: String = <rpc-url>,
  
  // Set rootchain contract. See below for more information
  override var ROOT_CONTRACT_ADDRESS: String = <root-contract-address>,
  
  // Set withdraw-manager Address. See below for more information
  override var WITHDRAW_MANAGER_ADDRESS: String = <withdraw-manager-address>,
  
  // Set deposit-manager Address. See below for more information  
  override var DEPOSIT_MANAGER_ADDRESS: String = <deposit-manager-address>,
  
  // Syncer API URL
  // Fetches tx/receipt proof data instead of fetching whole block on client side
  override var SYNCER_URL: String = "https://matic-syncer2.api.matic.network/api/v1",
  
  // Watcher API URL
  // Fetches headerBlock info from mainchain & finds appropriate headerBlock for given blockNumber
  override var WATCHER_URL: String = "https://ropsten-watcher2.api.matic.network/api/v1",
  
  // Set matic network's WETH address. See below for more information
  override var MATIC_WETH_ADDRESS: String = <matic-weth-address>,

  // Set WETH address on RootChain. See below for more information
  override var ROOT_WETH_ADDRESS: String = <root-weth-address>
) : NetworkConfig()

// Create sdk instance
val maticInstance = Matic(TestNet1())

// Set wallet
maticInstance.setWallet(ConfigExample.PRIVATE_KEY)

// Set address
maticInstance.setFromAddress(ConfigExample.FROM_ADDRESS, true)

// get web3 instance
val web3 = Matic.getWeb3j(provider) // Provider Example: 'https://ropsten.infura.io'

// get ERC20 token balance
maticInstance.getERC20Balance(
  ConfigExample.ROPSTEN_TEST_TOKEN, // User address
  userAddress, // Token address
  true // `Parent` must be boolean value. For token transfer on Main chain, use parent: true
)

// Deposit Ether into Matic chain
 maticInstance.depositEthers(
    BigInteger("3000000000000000000") // Amount
  )

// Approve ERC20 token for deposit
  maticInstance.approveERC20TokensForDeposit(
    ConfigExample.ROPSTEN_TEST_TOKEN,
    BigInteger("10000000000000000000")
  )

// Deposit token into Matic chain. Remember to call `approveERC20TokensForDeposit` before
maticInstance.depositERC20Tokens(
  ConfigExample.ROPSTEN_TEST_TOKEN,
  BigInteger("10000000000000000")
)

// Transfer token on Matic
maticInstance.transferTokens(
  recipientAddress,
  ConfigExample.ROPSTEN_TEST_TOKEN,
  BigInteger("1000000000000000"),
  false
)

// Initiate withdrawal of ERC20 from Matic and retrieve the Transaction id
maticInstance.startWithdraw(
  ConfigExample.MATIC_TEST_TOKEN,
  BigInteger("100000000000000000")
)

// Withdraw funds from the Matic chain using the Transaction id generated from the 'startWithdraw' method
// after header has been submitted to mainchain
maticInstance.withdraw(transactionHash)

 ```
