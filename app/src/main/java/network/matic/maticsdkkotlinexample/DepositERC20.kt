package network.matic.maticsdkkotlinexample

import io.reactivex.schedulers.Schedulers
import network.matic.sdk.Matic
import java.math.BigInteger

object DepositERC20 {

  fun approveERC20Example() {
    val maticInstance = Matic(TestNet1())
    maticInstance.setWallet(ConfigExample.PRIVATE_KEY)

    maticInstance.approveERC20TokensForDeposit(
      ConfigExample.ROPSTEN_TEST_TOKEN,
      BigInteger("10000000000000000000")
    ).subscribeOn(Schedulers.io())
      .subscribe({
        println("Transaction Hash:  ${it.transactionHash}")

      }, {
        it.printStackTrace()
      })
  }

  fun depositERC20Example() {
    val maticInstance = Matic(TestNet1())
    maticInstance.setWallet(ConfigExample.PRIVATE_KEY)

    maticInstance.depositERC20Tokens(
      ConfigExample.ROPSTEN_TEST_TOKEN,
      BigInteger("10000000000000000")
    ).subscribeOn(Schedulers.io())
      .subscribe({
        println("Transaction Hash:  ${it.transactionHash}")

      }, {
        it.printStackTrace()
      })
  }
}