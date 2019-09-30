package network.matic.maticsdkkotlinexample

import io.reactivex.schedulers.Schedulers
import network.matic.sdk.Matic
import java.math.BigInteger

object TransferERC20 {
  fun transferERC20Example() {
    val recipientAddress = "0xf66f409086647591e0c2f122c1945554b8e0e74f"
    val maticInstance = Matic(TestNet1())
    maticInstance.setWallet(ConfigExample.PRIVATE_KEY)

    maticInstance.transferTokens(
      recipientAddress,
      ConfigExample.ROPSTEN_TEST_TOKEN,
      BigInteger("1000000000000000"),
      false
    )
      .subscribeOn(Schedulers.io())
      .subscribe({
        println("Transaction Hash:  ${it.transactionHash}")
      }, {
        it.printStackTrace()
      })
  }

}