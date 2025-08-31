package com.example.odyssey.core.di

import androidx.room.Room
import com.example.odyssey.core.database.TravelDatabase
import com.example.odyssey.data.remote.AIPlanningApiService
import com.example.odyssey.data.remote.AuthInterceptor
import com.example.odyssey.data.remote.FourSquareApiService
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
            .addInterceptor(AuthInterceptor(FourSquareApiService.API_KEY))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(FourSquareApiService.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create(FourSquareApiService::class.java) }
    single { get<Retrofit>().create(AIPlanningApiService::class.java) }
}

