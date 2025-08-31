package com.example.odyssey.data.models

import com.google.gson.annotations.SerializedName


data class TripPlanningRequest(
    @SerializedName("destination")
    val destination: String,

    @SerializedName("start_date")
    val startDate: String,

    @SerializedName("end_date")
    val endDate: String,

    @SerializedName("travelers")
    val travelers: Int,

    @SerializedName("budget")
    val budget: Double,

    @SerializedName("interests")
    val interests: List<String>,

    @SerializedName("travel_style")
    val travelStyle: String,

    @SerializedName("accommodation_type")
    val accommodationType: String

)

data class AIItineraryResponse(
    @SerializedName("itinerary")
    val itinerary: GeneratedItinerary,

    @SerializedName("suggestions")
    val suggestions: List<String> = emptyList(),

    @SerializedName("estimated_cost")
    val estimatedCost: Double = 0.0
)

data class GeneratedItinerary(
    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("days")
    val days: List<ItineraryDay>,

    @SerializedName("tips")
    val tips: List<String> = emptyList()
)

data class ItineraryDay(
    @SerializedName("day")
    val dayNumber: Int,

    @SerializedName("date")
    val date: String,

    @SerializedName("theme")
    val theme: String,

    @SerializedName("activities")
    val activities: List<ActivityItem>
)

data class ActivityItem(
    @SerializedName("time")
    val time: String,

    @SerializedName("activity")
    val activity: String,

    @SerializedName("location")
    val location: String,

    @SerializedName("duration")
    val duration: String,

    @SerializedName("cost")
    val estimatedCost: Double?,

    @SerializedName("description")
    val description: String,

    @SerializedName("tips")
    val tips: List<String> = emptyList(),

    @SerializedName("category")
    val category: String // "sightseeing", "food", "adventure", "culture", "relaxation"
)

// Conversation Flow Models
data class PlanningStep(
    val id: String,
    val question: String,
    val type: StepType,
    val options: List<String>? = null,
    val isRequired: Boolean = true,
    val userAnswer: String? = null,
    val isCompleted: Boolean = false
)

enum class StepType {
    TEXT_INPUT,
    SINGLE_CHOICE,
    MULTIPLE_CHOICE,
    DATE_PICKER,
    NUMBER_PICKER,
    BUDGET_SLIDER
}