package com.cesoft.cesrssreader2

import android.app.Application
import com.cesoft.cesrssreader2.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule, remoteModule, localModule, repoModule)
        }
    }

    companion object {
        private val TAG: String = App::class.simpleName!!
    }
}