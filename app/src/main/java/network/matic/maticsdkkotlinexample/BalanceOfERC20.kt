package network.matic.maticsdkkotlinexample

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import network.matic.sdk.Matic

object BalanceOfERC20 {
  fun balanceOfERC20Example() {
    val userAddress = "0x7eD7f36694153bA6EfF6ca6726b60F6E2Bb17fcf"
    val maticInstance = Matic(TestNet1())
    maticInstance.setFromAddress(ConfigExample.FROM_ADDRESS, true)
    maticInstance.getERC20Balance(ConfigExample.ROPSTEN_TEST_TOKEN, userAddress, true)
     .subscribeOn(Schedulers.io())
     .observeOn(AndroidSchedulers.mainThread())
     .subscribe({
       println("Balance $it")
     }, {
       it.printStackTrace()
     })
  }
}