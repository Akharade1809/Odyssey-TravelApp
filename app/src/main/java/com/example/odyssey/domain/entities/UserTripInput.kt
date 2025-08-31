package com.example.odyssey.domain.entities

import kotlinx.datetime.LocalDate

data class UserTripInput(
    val destination: String = "",
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val travelers: Int = 1,
    val budget: Double = 1000.0,
    val interests: List<String> = emptyList(),
    val travelStyle: String = "balanced",
    val accommodationType: String = "hotel"
)
