package network.matic.maticsdkkotlin

import io.reactivex.schedulers.Schedulers
import network.matic.matick.MaticK
import java.math.BigInteger

object TransferERC20 {
  fun testTransferERC20() {
    val recipientAddress = "0xf66f409086647591e0c2f122c1945554b8e0e74f"
    val matick = MaticK(TestNet1())
    matick.setWallet(ConfigTest.PRIVATE_KEY)

    matick.transferTokens(
      recipientAddress,
      ConfigTest.ROPSTEN_TEST_TOKEN,
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