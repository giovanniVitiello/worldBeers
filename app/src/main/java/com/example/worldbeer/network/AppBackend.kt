package com.example.worldbeer.network

import android.content.res.Resources
import com.example.worldbeer.R
import com.example.worldbeer.ui.home.model.BeerResponse
import com.google.gson.Gson
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class AppBackend(
    gson: Gson,
    networkThread: Scheduler = Schedulers.io(),
    resources: Resources
) {
    private val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val privateOkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    private val api = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(networkThread))
        .baseUrl(resources.getString(R.string.base_url))
        .client(privateOkHttpClient)
        .build()
        .create(ListingApi::class.java)

    fun getBeerList(): Single<BeerResponse> = api.getBeerList()

    private interface ListingApi {

        @GET("/v2/beers")
        fun getBeerList(): Single<BeerResponse>

    }
}