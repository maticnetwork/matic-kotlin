package network.matic.maticsdkkotlin

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import network.matic.matick.Matick

object TestMaticK {
    fun initWeb3() {
        var matick = Matick()
        matick.getGasPrice()
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                println("gasPRice ${it.gasPrice}")
            },{
                it.printStackTrace()
            })
    }
}