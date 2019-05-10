package com.koperko.jll

import android.app.Application
import com.koperko.jll.di.modules.appModule
import com.koperko.jll.di.modules.interactorsModule
import com.koperko.jll.di.modules.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Android context
            androidContext(this@App)
            // modules
            modules(appModule, networkModule, interactorsModule)
        }
    }

}