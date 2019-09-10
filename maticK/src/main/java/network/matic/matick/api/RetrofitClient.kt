package network.matic.matick.api

import io.reactivex.Single
import okhttp3.OkHttpClient

import network.matic.matick.model.TransactionModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url


//interface Transaction {
//
//    @GET
//    fun getTransaction(@Url url: String): Call<TransactionModel>
//
//}
//
//class TransactionApi : Transaction {
//    private var retrofit: Retrofit? = null
//    private var okHttpClient: OkHttpClient? = null
//
//
//
//    override fun getTransaction(url: String): Single<TransactionModel> {
//        if (okHttpClient == null) {
//            val builder = OkHttpClient().newBuilder()
//            builder.addInterceptor { chain ->
//                val request = chain.request().newBuilder()
//                    .build()
//                chain.proceed(request)
//            }
//            okHttpClient = builder.build()
//        }
//
//        if (retrofit == null) {
//            retrofit = Retrofit.Builder()
//                .baseUrl("https://matic-syncer2.api.matic.network/api/v1/tx/0xd8a23083d6ad4d0f081c180450bbab964c25263a6b225b98751406c23e54cb31")
//                .client(okHttpClient!!)
////                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//        }
//        return retrofit!!.create(Transaction::class.java).getTransaction(url)
//    }
//
//}