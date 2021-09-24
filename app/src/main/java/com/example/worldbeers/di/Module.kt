package com.example.worldbeers.di

import com.example.worldbeers.network.AppBackend
import com.example.worldbeers.network.AppContract
import com.example.worldbeers.network.AppProvider
import com.example.worldbeers.ui.home.HomeViewModel
import com.example.worldbeers.utils.sharedpreferences.KeyValueStorageFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val androidComponents = module {
    single { androidContext().resources }
    single { KeyValueStorageFactory.build(context = androidContext(), name = "WorldBeer") }
}

val appComponents = module {
    single { createGson() }
    single { AppProvider(backend = get()) } bind AppContract::class
    single { AppBackend(gson = get()) }
}

val viewModels = module {
    viewModel { HomeViewModel(scheduler = AndroidSchedulers.mainThread(), contract = get()) }
}

private fun createGson(): Gson = GsonBuilder().create()
