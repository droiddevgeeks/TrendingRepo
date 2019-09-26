package com.example.trendingrepo

import android.app.Application
import com.example.trendingrepo.di.Modules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TrendingApp: Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TrendingApp)
            modules(Modules.getAll())
        }
    }
}