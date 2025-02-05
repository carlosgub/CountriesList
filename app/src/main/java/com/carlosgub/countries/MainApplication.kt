package com.carlosgub.countries

import android.app.Application
import com.carlosgub.countries.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // inject Android context
            androidContext(this@MainApplication)
            modules(appModules)
        }
    }
}
