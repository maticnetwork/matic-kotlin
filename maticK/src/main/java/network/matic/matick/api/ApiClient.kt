package network.matic.matick.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import network.matic.matick.model.MyDeserializer
import com.google.gson.GsonBuilder
import com.google.gson.Gson
import network.matic.matick.model.TransactionModel


object ApiClient {
    private const val BASE = "https://matic-syncer2.api.matic.network/api/v1/tx/0xd8a23083d6ad4d0f081c180450bbab964c25263a6b225b98751406c23e54cb31/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .method(original.method, original.body)

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()
    var gson = GsonBuilder()
        .registerTypeAdapter(TransactionModel::class.java, MyDeserializer())
        .create()

    val instance: Api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        retrofit.create(Api :: class.java)
    }
}