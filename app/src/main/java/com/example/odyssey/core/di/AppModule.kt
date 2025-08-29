package com.example.odyssey.core.di

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.odyssey.core.database.TravelDatabase
import com.example.odyssey.presentation.ui.home.HomeViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    //Database
    single {
        Room.databaseBuilder(
            androidContext(),
            TravelDatabase::class.java,
            TravelDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<TravelDatabase>().destinationDao() }

    //Network
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.example.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

