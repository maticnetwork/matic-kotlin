package network.matic.sdk

import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import network.matic.sdk.api.SyncerApiFactory
import network.matic.sdk.api.WatcherApiFactory
import network.matic.sdk.artifacts.*
import network.matic.sdk.core.protocol.Web3j
import network.matic.sdk.core.protocol.core.DefaultBlockParameterName
import network.matic.sdk.core.protocol.core.methods.response.EthSendTransaction
import network.matic.sdk.core.protocol.http.HttpService
import network.matic.sdk.core.tx.RawTransactionManager
import network.matic.sdk.core.tx.TransactionManager
import network.matic.sdk.core.tx.gas.ContractGasProvider
import network.matic.sdk.crypto.*
import network.matic.sdk.model.Header
import network.matic.sdk.model.TransactionModel
import network.matic.sdk.model.TxProofModel
import network.matic.sdk.model.WatcherModel
import network.matic.sdk.rlp.RlpDecoder
import network.matic.sdk.rlp.RlpEncoder
import network.matic.sdk.rlp.RlpString
import network.matic.sdk.rlp.RlpType
import network.matic.sdk.utils.utils.Numeric
import java.math.BigInteger
import java.nio.ByteBuffer

class Matic(networkConfig: NetworkConfig) {
  private lateinit var credentials: Credentials
  private lateinit var fromAddress: String
  private lateinit var transactionManager: TransactionManager
  private var isCredentials: Boolean = false
  private var web3j: Web3j = getWeb3j(networkConfig.MATIC_PROVIDER)
  private var web3jParent: Web3j = getWeb3j(networkConfig.PARENT_PROVIDER)
  private var rootChainAddress: String = networkConfig.ROOT_CONTRACT_ADDRESS
  private var rootWethAddress: String = networkConfig.ROOT_WETH_ADDRESS
  private var childWethAddress: String = networkConfig.MATIC_WETH_ADDRESS
  private var withdrawManagerAddress: String = networkConfig.WITHDRAW_MANAGER_ADDRESS
  private var depositManagerAddress: String = networkConfig.DEPOSIT_MANAGER_ADDRESS
  private var syncerApiFactory = SyncerApiFactory(networkConfig)
  private var watcherApiFactory = WatcherApiFactory(networkConfig)

  fun loadERC20Contract(contractAddress: String, parent: Boolean = false): Flowable<ChildERC20> {
    return when (isCredentials) {
      true -> Flowable.just(
        ChildERC20.load(
          contractAddress,
          if (parent) web3jParent else web3j,
          credentials,
          CustomContractGasProvider()
        )
      )
      false -> Flowable.just(
        ChildERC20.load(
          contractAddress,
          if (parent) web3jParent else web3j,
          transactionManager,
          CustomContractGasProvider()
        )
      )
    }
  }

  fun loadERC721Contract(contractAddress: String, parent: Boolean = false): Flowable<ChildERC721> {
    return when (isCredentials) {
      true -> Flowable.just(
        ChildERC721.load(
          contractAddress,
          if (parent) web3jParent else web3j,
          credentials,
          CustomContractGasProvider()
        )
      )
      false -> Flowable.just(
        ChildERC721.load(
          contractAddress,
          if (parent) web3jParent else web3j,
          transactionManager,
          CustomContractGasProvider()
        )
      )
    }
  }

  fun loadRootChainContract(): Flowable<RootChain> {
    return when (isCredentials) {
      true -> Flowable.just(
        RootChain.load(
          rootChainAddress,
          web3jParent,
          credentials,
          CustomContractGasProvider()
        )
      )
      false -> Flowable.just(
        RootChain.load(
          rootChainAddress,
          web3jParent,
          transactionManager,
          CustomContractGasProvider()
        )
      )
    }
  }

  fun loadStandardTokenContract(
    contractAddress: String,
    parent: Boolean = false
  ): Flowable<StandardToken> {
    return when (isCredentials) {
      true -> Flowable.just(
        StandardToken.load(
          contractAddress,
          if (parent) web3jParent else web3j,
          credentials,
          CustomContractGasProvider()
        )
      )
      false -> Flowable.just(
        StandardToken.load(
          contractAddress,
          if (parent) web3jParent else web3j,
          transactionManager,
          CustomContractGasProvider()
        )
      )
    }
  }

  fun loadDepositMangerContract(): Flowable<DepositManager> {
    return when (isCredentials) {
      true -> Flowable.just(
        DepositManager.load(
          depositManagerAddress,
          web3jParent,
          credentials,
          CustomContractGasProvider()
        )
      )
      false -> Flowable.just(
        DepositManager.load(
          depositManagerAddress,
          web3jParent,
          transactionManager,
          CustomContractGasProvider()
        )
      )
    }
  }

  fun loadWithdrawMangerContract(): Flowable<WithdrawManager> {
    return when (isCredentials) {
      true -> Flowable.just(
        WithdrawManager.load(
          withdrawManagerAddress,
          web3jParent,
          credentials,
          CustomContractGasProvider()
        )
      )
      false -> Flowable.just(
        WithdrawManager.load(
          withdrawManagerAddress,
          web3jParent,
          transactionManager,
          CustomContractGasProvider()
        )
      )
    }
  }

  fun getEtherBalance(userAddress: String) = web3jParent.ethGetBalance(
    userAddress,
    DefaultBlockParameterName.LATEST
  ).flowable()

  fun getGasPrice() = web3jParent.ethGasPrice().flowable()

  fun getNonce() = web3j.ethGetTransactionCount(
    fromAddress, DefaultBlockParameterName.PENDING
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
    /**
     * TODO
     */
  }

  fun depositEthers(amount: BigInteger): Flowable<EthSendTransaction> {
    return loadRootChainContract()
      .flatMapSingle {
        it.depositEthers(amount)
      }.flatMap {
        signAndSendRawTransaction(it, web3jParent)
      }
  }

  fun approveERC20TokensForDeposit(
    contractAddress: String,
    amount: BigInteger
  ): Flowable<EthSendTransaction> {
    return loadStandardTokenContract(contractAddress, true)
      .flatMapSingle {
        it.approve(rootChainAddress, amount)
      }
      .flatMap {
        signAndSendRawTransaction(it, web3jParent)
      }
  }

  fun depositERC20Tokens(
    contractAddress: String,
    amount: BigInteger
  ): Flowable<EthSendTransaction> {
    return loadRootChainContract()
      .flatMapSingle {
        it.deposit(contractAddress, fromAddress, amount)
      }.flatMap {
        signAndSendRawTransaction(it, web3jParent)
      }
  }

  fun safeDepositERC721Tokens(
    contractAddress: String,
    amount: BigInteger
  ): Flowable<EthSendTransaction> {
    return loadERC721Contract(contractAddress, true)
      .flatMapSingle {
        it.safeTransferFrom(
          fromAddress,
          rootChainAddress,
          amount
        )
      }.flatMap {
        signAndSendRawTransaction(it, web3jParent)
      }
  }

  fun approveERC721TokenForDeposit(
    contractAddress: String,
    amount: BigInteger
  ): Flowable<EthSendTransaction> {
    return loadERC721Contract(contractAddress, true)
      .flatMapSingle {
        it.approve(rootChainAddress, amount)
      }.flatMap {
        signAndSendRawTransaction(it, web3jParent)
      }
  }

  fun depositERC721Tokens(
    contractAddress: String,
    amount: BigInteger
  ): Flowable<EthSendTransaction> {
    return loadRootChainContract()
      .flatMapSingle {
        it.depositERC721(contractAddress, fromAddress, amount)
      }.flatMap {
        signAndSendRawTransaction(it, web3jParent)
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
          signAndSendRawTransaction(it, this)
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
        signAndSendRawTransaction(it, this)
      }
    }
  }

  fun transferEthers(contractAddress: String, parent: Boolean = false) {
    /**
     * TODO
     */
  }

  fun startWithdraw(
    contractAddress: String,
    amount: BigInteger
  ): Flowable<EthSendTransaction> {
    return loadERC20Contract(contractAddress, false)
      .flatMapSingle {
        it.withdraw(amount)
      }
      .flatMap {
        signAndSendRawTransaction(it, web3j)
      }
  }

  fun startERC721Withdraw(
    contractAddress: String,
    amount: BigInteger
  ): Flowable<EthSendTransaction> {
    return loadERC721Contract(contractAddress)
      .flatMapSingle {
        it.withdraw(amount)
      }.flatMap {
        signAndSendRawTransaction(it, web3j)
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
    txHash: String
  ): Flowable<EthSendTransaction> {
    return getTxProof(txHash)
      .zipWith(getReceiptProof(txHash),
        BiFunction { txProof: TxProofModel, receiptProof: TxProofModel ->
          Proof(txProof, receiptProof)
        }).flatMapPublisher {
        val header = getHeaderObject(it.txProofModel.proof.blockNumber)
        val headerNumber = header.blockingGet().number

        val headerProof = getHeaderProof(
          it.txProofModel.proof.blockNumber,
          header.blockingGet().start,
          header.blockingGet().end
        )

        val trimmedProof =
          headerProof.blockingGet().proof.proof.joinToString("").replace("0x", "")

        val sPath = it.receiptProofModel.proof.path
        val t = Numeric.hexStringToByteArray(sPath)
        val values = ArrayList<RlpType>()

        values.add(RlpString.create(sPath))

        // TODO: Offset is hardcoded here, not good approach need to think on this
        // val rlpList = RlpList(values)
        // val encoderRlp = RlpEncoder.encode(rlpList)

        val encoderRlp = RlpEncoder.encode(t, RlpDecoder.OFFSET_SHORT_STRING)

        val hexValue = Numeric.toHexString(encoderRlp)

        withdrawBurntTokens(
          headerNumber,
          it.txProofModel,
          it.receiptProofModel,
          trimmedProof,
          hexValue
        )
      }.flatMap {
        signAndSendRawTransaction(it, web3jParent)
      }.onErrorReturn {
        throw Exception("No corresponding checkpoint/header block found for $txHash")
      }
  }


  private fun withdrawBurntTokens(
    headerNumber: String,
    txProof: TxProofModel,
    receiptProof: TxProofModel,
    trimmedProof: String,
    hexValue: String
  ): Flowable<RawTransaction> {
    return loadWithdrawMangerContract()
      .flatMapSingle {
        it.withdrawBurntTokens(
          BigInteger.valueOf(headerNumber.toLong()),
          Numeric.hexStringToByteArray(trimmedProof),
          BigInteger.valueOf(txProof.proof.blockNumber.toLong()),
          BigInteger.valueOf(txProof.proof.blockTimestamp.toLong()),
          Numeric.hexStringToByteArray(txProof.proof.root),
          Numeric.hexStringToByteArray(receiptProof.proof.root),
          Numeric.hexStringToByteArray(hexValue),
          Numeric.hexStringToByteArray(txProof.proof.value),
          Numeric.hexStringToByteArray(txProof.proof.parentNodes),
          Numeric.hexStringToByteArray(receiptProof.proof.value),
          Numeric.hexStringToByteArray(receiptProof.proof.parentNodes)
        )
      }
  }

  fun processExits(rootTokenAddress: String)
      : Flowable<EthSendTransaction> {
    return loadWithdrawMangerContract()
      .flatMapSingle {
        it.processExits(rootTokenAddress)
      }.flatMap {
        signAndSendRawTransaction(it, web3jParent)
      }
  }

  fun setWallet(privateKey: String) {
    credentials = Credentials.create(privateKey)
    isCredentials = true
    fromAddress = credentials.address
  }

  fun setFromAddress(address: String, parent: Boolean = false) {
    fromAddress = address
    transactionManager = RawTransactionManager(
      if (parent) web3jParent else web3j,
      fromAddress
    )
  }

  fun signAndSendRawTransaction(rawTransaction: RawTransaction, web3j: Web3j)
      : Flowable<EthSendTransaction> {
    val signedTransaction = TransactionEncoder.signMessage(rawTransaction, credentials)
    val hexValue = Numeric.toHexString(signedTransaction)
    return web3j.ethSendRawTransaction(hexValue).flowable()
  }

  fun signTypedData(msgParams: String): String {
    val dataEncoder = StructuredDataEncoder(msgParams)

    val sig = Sign.signMessage(dataEncoder.hashStructuredData(), credentials.ecKeyPair, false)
    return toRpcSig(sig)
  }

  fun signMessage(msgParams: String) : String {
    val msg = Numeric.hexStringToByteArray(msgParams)
    val signedMsg = Sign.signMessage(msg, credentials.ecKeyPair, false)
    return toRpcSig(signedMsg)
  }

  fun signPersonalMessage(msgParams: String) : String {
    val msg = Numeric.hexStringToByteArray(msgParams)
    val signedMsg = Sign.signPrefixedMessage(msg, credentials.ecKeyPair)
    return toRpcSig(signedMsg)
  }

  private fun toRpcSig(signature: Sign.SignatureData): String {
    // sign
    val sigBuffer = ByteBuffer.allocate(signature.r.size + signature.s.size + 1)
    sigBuffer.put(signature.r)
    sigBuffer.put(signature.s)
    sigBuffer.put(signature.v)

    return Numeric.toHexString(sigBuffer.array())
  }

  companion object{
    fun getWeb3j(rpc: String) :Web3j {
      return Web3j.build(HttpService(rpc))
    }
  }
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

internal class Proof(
  val txProofModel: TxProofModel,
  val receiptProofModel: TxProofModel
)
