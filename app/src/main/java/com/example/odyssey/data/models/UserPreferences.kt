package com.example.odyssey.data.models

data class UserPreferences(
    val travelStyle: String = "adventurer",
    val preferredBudget: Double = 1000.0,
    val currency: String = "USD",
    val favoriteCategories: List<String> = emptyList(),
    val isFirstLaunch: Boolean = true,
    val userName: String = "Explorer",
    val notificationEnabled: Boolean = true,
    val themeMode: String = "system",
    val language: String = "en",
    val distanceUnit: String = "km"
)
