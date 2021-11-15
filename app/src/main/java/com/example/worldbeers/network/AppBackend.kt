package com.example.worldbeers.network

import com.example.worldbeers.BuildConfig
import com.example.worldbeers.ui.home.model.BeerResponse
import com.google.gson.Gson
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.supervisorScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class AppBackend(
    gson: Gson,
    networkThread: Scheduler = Schedulers.io()
) {
    private val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val privateOkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    private val api = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(networkThread))
        .baseUrl(BuildConfig.BASE_API_URL)
        .client(privateOkHttpClient)
        .build()
        .create(ListingApi::class.java)

    fun getBeerList(): Response<Single<List<BeerResponse>>> = api.getBeerList()

    suspend fun getBeerListCoroutines(): Response<List<BeerResponse>> = api.getBeerListCoroutines()

    interface ListingApi {

        @GET("/v2/beers")
        fun getBeerList(): Response<Single<List<BeerResponse>>>

        @GET("/v2/beers")
        suspend fun getBeerListCoroutines(): Response<List<BeerResponse>>
    }
}
