package com.example.odyssey.domain.usecases

import org.koin.dsl.module

val usecaseModule = module {
    single { GetDestinationByCategory(get()) }
    single { GetPopularDestinationUseCase(get()) }
    single { GetUserPreferencesUseCase(get()) }
    single { UpdateUserPreferencesUseCase(get()) }
}