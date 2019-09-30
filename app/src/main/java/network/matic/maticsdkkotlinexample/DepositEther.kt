package network.matic.maticsdkkotlinexample

import io.reactivex.schedulers.Schedulers
import network.matic.sdk.Matic
import java.math.BigInteger

object DepositEther {
  fun depositEtherExample() {
    val maticInstance = Matic(TestNet1())
    maticInstance.setWallet(ConfigTest.PRIVATE_KEY)

    maticInstance.depositEthers(
      BigInteger("3000000000000000000")
    )
      .subscribeOn(Schedulers.io())
      .subscribe({
        println("Transaction Hash:  ${it.transactionHash}")
      }, {
        it.printStackTrace()
      })
  }

}