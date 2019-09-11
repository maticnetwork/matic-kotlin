package network.matic.matick.api

import com.google.gson.Gson
import network.matic.matick.ConfigUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object SyncerApiFactory {
    private const val BASE = ConfigUtils.SYNCER_URL

    private var retrofit: Retrofit? = null


    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .method(original.method, original.body)

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()


    fun getRetrofitInstance(): SyncerApi {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
        }
        return retrofit!!.create(SyncerApi::class.java)
    }
}