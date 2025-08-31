package com.example.odyssey.presentation.ui

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.odyssey.presentation.ui.home.HomeViewModel
import com.example.odyssey.presentation.ui.tripPlanning.TripPlanningViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { HomeViewModel(get(), get(), get(), get()) }

    single { TripPlanningViewModel(get(), get()) }
}