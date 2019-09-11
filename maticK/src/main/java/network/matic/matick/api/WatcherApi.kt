package network.matic.matick.api

import io.reactivex.Single
import network.matic.matick.model.WatcherModel
import retrofit2.http.GET
import retrofit2.http.Path

interface WatcherApi {

    @GET("header/included/{blockNumber}")
    fun getHeaderObject(@Path("blockNumber") blockNumber: String): Single<WatcherModel>

}