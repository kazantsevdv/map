package com.example.map

import android.app.Application
import com.example.map.di.databaseModule
import com.example.map.di.model
import com.example.map.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)

            modules(
                listOf(
                    databaseModule,
                    repositoryModule,
                    model
                )
            )
        }
    }
}