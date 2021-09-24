package com.example.worldbeers

import android.annotation.SuppressLint
import androidx.multidex.MultiDexApplication
import com.example.worldbeers.di.androidComponents
import com.example.worldbeers.di.appComponents
import com.example.worldbeers.di.viewModels
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import timber.log.Timber

const val TAG_LOGGING = "WORLDBEERS"

class WorldBeers : MultiDexApplication() {
    @SuppressLint("StringFormatInvalid")
    override fun onCreate() {
        super.onCreate()
        setupDI()
        setupLogging()

    }

    private fun setupDI() {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@WorldBeers)

            val appSetupModule = module {
                single { BuildConfig.DEBUG }
            }

            modules(
                listOf(
                    appSetupModule,
                    androidComponents,
                    appComponents,
                    viewModels
                )
            )
        }
    }

    private fun setupLogging() {
        Timber.plant(Timber.DebugTree())
        Timber.tag(TAG_LOGGING)
    }
}
