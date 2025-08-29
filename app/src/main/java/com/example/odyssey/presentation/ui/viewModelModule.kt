package com.example.odyssey.presentation.ui

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.odyssey.presentation.ui.home.HomeViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { HomeViewModel(get(), get(), get()) }
}