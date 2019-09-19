package network.matic.maticsdkkotlin

import io.reactivex.schedulers.Schedulers
import network.matic.matick.MaticK
import network.matic.matick.crypto.Credentials
import java.math.BigInteger

object TestMaticK {
  fun initWeb3() {

    val matick = MaticK(TestNet1())
    matick.setWallet(ConfigTest.PRIVATE_KEY)
//        matick.getGasPrice()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                println("gasPRice ${it.gasPrice}")
//            },{
//                it.printStackTrace()
//            })

//        matick.loadERC20Contract("0xc82c13004c06E4c627cF2518612A55CE7a3Db699")
//            .subscribeOn(Schedulers.io())
//            .flatMap {
//                it.balanceOf("0x9fB29AAc15b9A4B7F17c3385939b007540f4d791").flowable()
//            }.subscribe( {
//                println("balance ${it}")
//            },
//           {
//               it.printStackTrace()
//           })

        matick.getERC20Balance(ConfigTest.ROPSTEN_TEST_TOKEN,ConfigTest.FROM_ADDRESS, true)
            .subscribeOn(Schedulers.io())
            .subscribe({
            println("Balance ${it}")
        },{
                println("errorrr")
            it.printStackTrace()
        })

        matick.transferTokens(ConfigTest.FROM_ADDRESS, ConfigTest.ROPSTEN_TEST_TOKEN, BigInteger.valueOf(100000000000000000), true)
            .subscribeOn(Schedulers.io())
            .subscribe({
                println("hello it ${it.transactionHash}")
            },{
                it.printStackTrace()
            })

//        matick.startWithdraw(
//            ConfigTest.MATIC_TEST_TOKEN,
//            BigInteger.valueOf(100000000000000000),
//            false
//        )
//            .subscribeOn(Schedulers.io())
//            .subscribe({
//                println("hello it ${it.transactionHash}")
//            }, {
//                it.printStackTrace()
//            })

//        matick.approveERC20TokensForDeposit(
//            ConfigTest.ROPSTEN_TEST_TOKEN,
//            BigInteger.valueOf(100000000000000000)
//        )
//            .subscribeOn(Schedulers.io())
//            .subscribe({
//                println("hello it ${it.transactionHash}")
//            }, {
//                it.printStackTrace()
//            })

//        matick.depositERC20Tokens(
//            ConfigTest.ROPSTEN_TEST_TOKEN,
//            BigInteger.valueOf(100000000000000000)
//        )
//            .subscribeOn(Schedulers.io())
//            .subscribe({
//                println("hello it ${it.transactionHash}")
//            }, {
//                it.printStackTrace()
//            })

//        matick.safeDepositERC721Tokens(
//            ConfigTest.ROPSTEN_TEST_TOKEN,
//            BigInteger.valueOf(8)
//        )
//            .subscribeOn(Schedulers.io())
//            .subscribe({
//                println("hello it ${it.transactionHash}")
//            }, {
//                it.printStackTrace()
//            })

//        matick.getTx("0xc79e89838582267224b4bb6491661b12dffdd0cf0fdc65c71bfb104dac532d56")
//        matick.getTxProof("0xc79e89838582267224b4bb6491661b12dffdd0cf0fdc65c71bfb104dac532d56")
//        matick.getReceiptProof("0xc79e89838582267224b4bb6491661b12dffdd0cf0fdc65c71bfb104dac532d56")
//    matick.withdraw(
//      ConfigTest.WITHDRAW_MANAGER_ADDRESS,
//      "0x21ec428212956adb841a89c17bf75c13e5781432ab1ab745106c83fb3639be3d"
////            "0x3a50a99a47d408887517d1dc086fdee631e134447e584394e1eeb6ff5a8fef69"
//    ).subscribeOn(Schedulers.io())
//      .subscribe({
//        println("hello it ${it.transactionHash}")
//      }, {
//        it.printStackTrace()
//      })
  }

}