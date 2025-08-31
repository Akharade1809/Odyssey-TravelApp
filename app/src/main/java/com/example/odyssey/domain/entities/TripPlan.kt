package com.example.odyssey.domain.entities

import kotlinx.datetime.LocalDate

data class TripPlan(
    val id : String,
    val title : String,
    val destination : String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val travelers: Int,
    val budget: Double,
    val currency: String,
    val itinerary: Itinerary,
    val createdAt: Long = System.currentTimeMillis(),
    val isGenerated: Boolean = false

)
