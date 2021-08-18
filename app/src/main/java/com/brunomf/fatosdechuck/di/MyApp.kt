package com.brunomf.fatosdechuck.di

import android.app.Application
import com.brunomf.fatosdechuck.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

open class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initiateKoin()
    }

    private fun initiateKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MyApp)

            modules(
                uiModule,
                remoteModule,
                repositoryModule,
            )
        }
    }
}
