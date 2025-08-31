package com.example.odyssey.domain.repository

import com.example.odyssey.data.repositoryImpl.DestinationRepositoryImpl
import com.example.odyssey.data.repositoryImpl.TripPlanningRepositoryImpl
import com.example.odyssey.data.repositoryImpl.UserPreferencesRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<DestinationRepository> { DestinationRepositoryImpl(get(), get()) }
    single<UserPreferencesRepository> { UserPreferencesRepositoryImpl(get()) }

    single<TripPlanningRepository> { TripPlanningRepositoryImpl(get()) }
}