package com.example.odyssey.domain.entities

enum class ActivityCategory(val displayName: String, val emoji: String) {
    SIGHTSEEING("Sightseeing", "🏛️"),
    FOOD("Food & Dining", "🍽️"),
    ADVENTURE("Adventure", "🎢"),
    CULTURE("Culture", "🎭"),
    RELAXATION("Relaxation", "🧘"),
    SHOPPING("Shopping", "🛍️"),
    NIGHTLIFE("Nightlife", "🌃"),
    TRANSPORTATION("Transportation", "🚗")
}