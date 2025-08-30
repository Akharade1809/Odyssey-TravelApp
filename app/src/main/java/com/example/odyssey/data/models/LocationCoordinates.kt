package com.example.odyssey.data.models

data class LocationCoordinates(
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float = 0f,
    val timestamp: Long = System.currentTimeMillis()
)
