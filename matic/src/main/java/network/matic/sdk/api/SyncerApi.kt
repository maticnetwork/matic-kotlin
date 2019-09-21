package network.matic.sdk.api

import io.reactivex.Single
import network.matic.sdk.model.Header
import network.matic.sdk.model.TransactionModel
import network.matic.sdk.model.TxProofModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SyncerApi {
//    @GET
//    fun getTransaction(@Url txHash: String): Call<TransactionModel>

    @GET("tx/{txHash}")
    fun getTransaction(@Path("txHash") txHash: String): Single<TransactionModel>

    @GET("tx/{txHash}/receipt")
    fun getTransactionReceipt(@Path("txHash") txHash: String): Single<TransactionModel>

    @GET("tx/{txHash}/proof")
    fun getTxProof(@Path("txHash") txHash: String): Single<TxProofModel>

    @GET("tx/{txHash}/receipt/proof")
    fun getReceiptProof(@Path("txHash") txHash: String): Single<TxProofModel>

    @GET("block/{blockNumber}/proof")
    fun getHeaderProof(@Path("blockNumber") blockNumber: String,
                       @Query("start") start: String,
                       @Query("end") end: String
        ) : Single<Header>

}