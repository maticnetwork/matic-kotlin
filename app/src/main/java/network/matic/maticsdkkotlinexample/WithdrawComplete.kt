package network.matic.maticsdkkotlinexample

import io.reactivex.schedulers.Schedulers
import network.matic.matick.MaticK

object WithdrawComplete {
  fun testWithdrawComplete() {
    val matick = MaticK(TestNet1())
    val transactionHash = "0x733fb1835cf781f16b9914baa95449dac0394278670874abde8951a443d4ba41"
    matick.setWallet(ConfigTest.PRIVATE_KEY)

    matick.withdraw(transactionHash)
      .subscribeOn(Schedulers.io())
      .subscribe({
        println("Transaction Hash:  ${it.transactionHash}")
      }, {
        it.printStackTrace()
        println(it.message)
      })
  }

}