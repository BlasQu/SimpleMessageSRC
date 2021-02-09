package com.example.simplemessage.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DIManager: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DIManager)
            modules(listOf(
                retrofitModule
            ))
        }
    }
}