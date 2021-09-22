package com.example.worldbeer.di

import com.example.worldbeer.ui.detail.MainViewModel
import com.example.worldbeer.utils.sharedpreferences.KeyValueStorageFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidComponents = module {
    single { androidContext().resources }
    single { KeyValueStorageFactory.build(context = androidContext(), name = "WorldBeer") }
}

val appComponents = module {
    single { createGson() }
//    single { Provider(backend = get()) } bind Contract::class
//    single { Backend(gson = get(), resources = get()) }
}

val viewModels = module {
    viewModel { MainViewModel() }
//    viewModel { SignIn1ViewModel(repository = get(), prefs = get()) }
}

private fun createGson(): Gson = GsonBuilder().create()
