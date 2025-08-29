package com.example.odyssey.domain.entities

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
){
    companion object {
        val TRAVEL_STYLES = listOf(
            "adventurer" to "🏔️ Adventurer",
            "luxury" to "✨ Luxury Traveler",
            "budget" to "💰 Budget Explorer",
            "cultural" to "🎭 Culture Enthusiast",
            "backpacker" to "🎒 Backpacker",
            "family" to "👨‍👩‍👧‍👦 Family Traveler",
            "business" to "💼 Business Traveler"
        )

        val CURRENCIES = listOf(
            "USD" to "$",
            "EUR" to "€",
            "GBP" to "£",
            "JPY" to "¥",
            "INR" to "₹",
            "AUD" to "A$",
            "CAD" to "C$"
        )
    }
}
