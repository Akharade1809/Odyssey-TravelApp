package com.example.odyssey.domain.entities

data class DayPlan(
    val dayNumber: Int,
    val date: kotlinx.datetime.LocalDate,
    val theme: String,
    val activities: List<Activity>,
    val estimateCost: Double
)
