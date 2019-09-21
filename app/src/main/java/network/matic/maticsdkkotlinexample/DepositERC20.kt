package network.matic.maticsdkkotlinexample

import io.reactivex.schedulers.Schedulers
import network.matic.sdk.Matic
import java.math.BigInteger

object DepositERC20 {

  fun testApproveERC20() {
    val maticInstance = Matic(TestNet1())
    maticInstance.setWallet(ConfigTest.PRIVATE_KEY)

    maticInstance.approveERC20TokensForDeposit(
      ConfigTest.ROPSTEN_TEST_TOKEN,
      BigInteger("10000000000000000000")
    ).subscribeOn(Schedulers.io())
      .subscribe({
        println("Transaction Hash:  ${it.transactionHash}")

      }, {
        it.printStackTrace()
      })
  }

  fun testDepositERC20() {
    val maticInstance = Matic(TestNet1())
    maticInstance.setWallet(ConfigTest.PRIVATE_KEY)

    maticInstance.depositERC20Tokens(
      ConfigTest.ROPSTEN_TEST_TOKEN,
      BigInteger("10000000000000000")
    ).subscribeOn(Schedulers.io())
      .subscribe({
        println("Transaction Hash:  ${it.transactionHash}")

      }, {
        it.printStackTrace()
      })
  }
}