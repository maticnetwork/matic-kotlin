package network.matic.sdk.api

import io.reactivex.Single
import network.matic.sdk.model.WatcherModel
import retrofit2.http.GET
import retrofit2.http.Path

interface WatcherApi {

    @GET("header/included/{blockNumber}")
    fun getHeaderObject(@Path("blockNumber") blockNumber: String): Single<WatcherModel>

}