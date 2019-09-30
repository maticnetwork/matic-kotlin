package network.matic.maticsdkkotlinexample

import io.reactivex.schedulers.Schedulers
import network.matic.sdk.Matic
import java.math.BigInteger

object WithdrawERC20 {
  fun withdrawERC20Example() {
    val maticInstance = Matic(TestNet1())
    maticInstance.setWallet(ConfigExample.PRIVATE_KEY)
    maticInstance.startWithdraw(
      ConfigExample.MATIC_TEST_TOKEN,
      BigInteger("100000000000000000")
    )
      .subscribeOn(Schedulers.io())
      .subscribe({
        println("Transaction Hash:  ${it.transactionHash}")
      }, {
        it.printStackTrace()
      })
  }

}