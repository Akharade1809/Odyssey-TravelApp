package com.example.odyssey.domain.entities

data class Itinerary(
    val title : String,
    val description: String,
    val days: List<DayPlan>,
    val totalEstimatedCost : Double,
    val tips: List<String>
)
