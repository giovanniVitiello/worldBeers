package com.example.regolo.di

import androidx.room.Room
import com.example.regolo.ui.detail.MainViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.example.regolo.database.AppDatabase
import com.example.regolo.database.AppRepository
import com.example.regolo.utils.Constants.Companion.DATABASE_NAME
import com.example.regolo.utils.sharedpreferences.KeyValueStorageFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidComponents = module {
    single { androidContext().resources }
    single { KeyValueStorageFactory.build(context = androidContext(), name = "Regolo") }
}

val appComponents = module {
    single { createGson() }
//    single { Provider(backend = get()) } bind Contract::class
//    single { Backend(gson = get(), resources = get()) }
}

val databaseComponents = module {
    //database.builder
    single { Room.databaseBuilder(androidContext(), AppDatabase::class.java, DATABASE_NAME).build() }

    //UsersDao()
    single { get<AppDatabase>().userDao() }
    //LocalsDao()
    single { get<AppDatabase>().localsDao() }
    //AllergieDao()
    single { get<AppDatabase>().allergieDao() }

    //repository
    single { AppRepository(get(), get(), get()) }
}

val viewModels = module {
    viewModel { MainViewModel() }
//    viewModel { SignIn1ViewModel(repository = get(), prefs = get()) }
}

private fun createGson(): Gson = GsonBuilder().create()
