package com.example.simplemessage.di

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DIManager: Application() {
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this@DIManager)
        startKoin {
            androidContext(this@DIManager)
            modules(listOf(
                    retrofitModule,
                    databaseModule,
                    architectureModule,
                    adaptersModule,
                    utilModule
            ))
        }
    }
}