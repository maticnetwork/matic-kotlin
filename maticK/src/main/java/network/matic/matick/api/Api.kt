package network.matic.matick.api

import network.matic.matick.model.TransactionModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface Api {
    @GET
    fun getTransaction(@Url txHash: String): Call<TransactionModel>

//    @GET("tx/{txHash}")
//    fun getTransaction(@Path("txHash") txHash: String): Call<TransactionModel>

    @GET("tx/{txHash}/receipt")
    fun getTransactionReceipt(@Path("txHash") txHash: String): Call<TransactionModel>


}