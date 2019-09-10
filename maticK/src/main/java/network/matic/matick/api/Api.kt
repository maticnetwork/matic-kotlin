package network.matic.matick.api

import network.matic.matick.model.TransactionModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface Api {
    @GET
    fun getTransaction(@Url url: String): Call<TransactionModel>

}