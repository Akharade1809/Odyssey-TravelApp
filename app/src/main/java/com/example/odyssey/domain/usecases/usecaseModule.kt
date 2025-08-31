package com.example.odyssey.domain.usecases

import org.koin.dsl.module

val usecaseModule = module {
    single { GetDestinationByCategoryUseCase(get()) }
    single { GetPopularDestinationUseCase(get()) }
    single { GetUserPreferencesUseCase(get()) }
    single { UpdateUserPreferencesUseCase(get()) }

    single { GenerateItineraryUseCase(get()) }
    single { SaveUserTripPlanUseCase(get()) }
    single { GetUserTripPlansUseCase(get()) }
    single { DeleteUserTripPlanUseCase(get()) }
}