package com.example.phelela_mind

import android.app.Application
import com.example.phelela_mind.di.databaseModule
import com.example.phelela_mind.di.repositoryModule
import com.example.phelela_mind.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PhelelaMindApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PhelelaMindApp)
            modules(
                databaseModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}
