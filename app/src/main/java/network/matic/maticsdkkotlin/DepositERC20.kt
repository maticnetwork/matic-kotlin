package network.matic.maticsdkkotlin

import io.reactivex.schedulers.Schedulers
import network.matic.matick.MaticK
import java.math.BigInteger

object DepositERC20 {
  fun testDepositERC20() {
    val matick = MaticK(TestNet1())
    matick.setWallet(ConfigTest.PRIVATE_KEY)

    matick.approveERC20TokensForDeposit(
      ConfigTest.ROPSTEN_TEST_TOKEN,
      BigInteger("10000000000000000000")
    ).flatMap {
      // Deposit ERC20 Tokens
      println("Transaction Hash:  ${it.transactionHash}")
      matick.depositERC20Tokens(
        ConfigTest.ROPSTEN_TEST_TOKEN,
        BigInteger("10000000000000000")
      )
    }
      .subscribeOn(Schedulers.io())
      .subscribe({
        println("Transaction Hash:  ${it.transactionHash}")

      }, {
        it.printStackTrace()
      })
  }
}