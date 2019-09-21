package network.matic.sdk;


abstract class NetworkConfig {
    abstract var MATIC_PROVIDER: String // This is the MATIC testnet RPC
    abstract var PARENT_PROVIDER: String // This is the Ropsten testnet RPC
    abstract var ROOT_CONTRACT_ADDRESS: String // The address for the main Plasma contract in  Ropsten testnet
    abstract var WITHDRAW_MANAGER_ADDRESS: String // An address for the WithdrawManager contract on Ropsten testnet
    abstract var DEPOSIT_MANAGER_ADDRESS: String  // An address for a DepositManager contract in Ropsten testnet
    abstract var SYNCER_URL: String // Backend service which syncs the Matic sidechain state to a MySQL database which we use for faster querying. This comes in handy especially for  ructing withdrawal proofs while exiting assets from Plasma.
    abstract var WATCHER_URL: String // Backend service which syncs the Matic Plasma contract events on Ethereum mainchain to a MySQL database which we use for faster querying. This comes in handy especially for listening to asset deposits on the Plasma contract.
    abstract var ROOT_WETH_ADDRESS: String  // This is a wrapped ETH ERC20 contract address so that we can support ETH deposits to the sidechain
    abstract var MATIC_WETH_ADDRESS: String // The corresponding wrapped ETH ERC20 contract address on the Matic chain
}
