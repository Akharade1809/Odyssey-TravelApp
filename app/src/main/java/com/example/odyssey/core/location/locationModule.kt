package com.example.odyssey.core.location

import android.location.Location
import org.koin.dsl.module

val locationModule = module {
    single { LocationManager(get()) }
}