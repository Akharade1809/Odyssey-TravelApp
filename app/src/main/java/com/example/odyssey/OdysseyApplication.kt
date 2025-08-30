package com.example.odyssey

import android.app.Application
import com.example.odyssey.core.di.appModule
import com.example.odyssey.core.location.locationModule
import com.example.odyssey.domain.repository.repositoryModule
import com.example.odyssey.domain.usecases.usecaseModule
import com.example.odyssey.presentation.ui.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class OdysseyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(org.koin.core.logger.Level.DEBUG)
            androidContext(this@OdysseyApplication)
            modules(
                // addition of koin modules here
                appModule,
                repositoryModule,
                viewModelModule,
                usecaseModule,
                locationModule,

            )
        }
    }
}