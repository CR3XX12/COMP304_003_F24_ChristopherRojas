package com.example.group13_comp304sec003_lab03

import android.app.Application
import com.example.group13_comp304sec003_lab03.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeathersApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(appModules)
        }
    }
}