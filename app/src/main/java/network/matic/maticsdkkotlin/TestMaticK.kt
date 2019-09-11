package network.matic.maticsdkkotlin

import io.reactivex.schedulers.Schedulers
import network.matic.matick.Matick
import java.math.BigInteger

object TestMaticK {
    fun initWeb3() {
        var matick = Matick()
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

//        matick.getERC20Balance("0xc82c13004c06E4c627cF2518612A55CE7a3Db699","0x9fB29AAc15b9A4B7F17c3385939b007540f4d791", false)
//            .subscribeOn(Schedulers.io())
//            .subscribe({
//            println("hello ${it}")
//        },{
//            it.printStackTrace()
//        })

        matick.transferTokens(ConfigTest.FROM_ADDRESS, ConfigTest.MATIC_TEST_TOKEN, BigInteger.valueOf(100000000000000000), false)
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
//        matick.withdraw( ConfigTest.WITHDRAWMANAGER_ADDRESS,"0xc79e89838582267224b4bb6491661b12dffdd0cf0fdc65c71bfb104dac532d56")

    }

}