package com.example.odyssey.domain.entities

data class Activity(
    val id: String,
    val time: String,
    val title: String,
    val location: String,
    val duration: String,
    val description: String,
    val category: ActivityCategory,
    val estimatedCost: Double?,
    val imageUrl: String?,
    val tips: List<String>
)
