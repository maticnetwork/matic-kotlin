package network.matic.maticsdkkotlin

import android.annotation.SuppressLint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import network.matic.matick.MaticK

object BalanceOfERC20 {
  fun testBalanceOfERC20() {
    val userAddress = "0x7eD7f36694153bA6EfF6ca6726b60F6E2Bb17fcf"
    val matick = MaticK(TestNet1())
    matick.setWallet(ConfigTest.PRIVATE_KEY)

    matick.getERC20Balance(ConfigTest.MATIC_TEST_TOKEN, userAddress, false)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe({
        println("Balance $it")
      }, {
        it.printStackTrace()
      })
  }
}