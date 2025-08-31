package com.example.odyssey.presentation.ui.tripPlanning

import com.example.odyssey.core.base.ViewEffect
import com.example.odyssey.core.base.ViewIntent
import com.example.odyssey.core.base.ViewState
import com.example.odyssey.data.models.PlanningStep
import com.example.odyssey.domain.entities.TripPlan
import com.example.odyssey.domain.entities.UserTripInput

data class TripPlanningState(
    val isLoading : Boolean = false,
    val currentStepIndex: Int = 0,
    val planningSteps: List<PlanningStep> = getInitialPlanningSteps(),
    val userInput: UserTripInput = UserTripInput(),
    val generatedTripPlan: TripPlan? = null,
    val error: String? = null,
    val showSuccess: Boolean = false,
    val canProceedToNext: Boolean = false,
    val canGoBack: Boolean = false
) : ViewState

sealed class TripPlanningIntent : ViewIntent {
    object StartPlanning : TripPlanningIntent()
    data class AnswerCurrentStep(val answer: String) : TripPlanningIntent()
    object NextStep : TripPlanningIntent()
    object PreviousStep : TripPlanningIntent()
    data class GoToStep(val stepIndex: Int) : TripPlanningIntent()
    object GenerateItinerary : TripPlanningIntent()
    object SaveTripPlan : TripPlanningIntent()
    object RestartPlanning : TripPlanningIntent()
}

sealed class TripPlanningEffect : ViewEffect {
    object NavigateToGeneratedPlan : TripPlanningEffect()
    object NavigateBack : TripPlanningEffect()
    data class ShowError(val message: String) : TripPlanningEffect()
    data class ShowSuccess(val message: String) : TripPlanningEffect()
}


fun getInitialPlanningSteps(): List<PlanningStep> {
    return listOf(
        PlanningStep(
            id = "destination",
            question = "🌍 Where would you like to go?",
            type = com.example.odyssey.data.models.StepType.TEXT_INPUT,
            isRequired = true
        ),
        PlanningStep(
            id = "start_date",
            question = "📅 When do you want to start your trip?",
            type = com.example.odyssey.data.models.StepType.DATE_PICKER,
            isRequired = true
        ),
        PlanningStep(
            id = "end_date",
            question = "📅 When will you return?",
            type = com.example.odyssey.data.models.StepType.DATE_PICKER,
            isRequired = true
        ),
        PlanningStep(
            id = "travelers",
            question = "👥 How many travelers?",
            type = com.example.odyssey.data.models.StepType.NUMBER_PICKER,
            isRequired = true
        ),
        PlanningStep(
            id = "budget",
            question = "💰 What's your budget?",
            type = com.example.odyssey.data.models.StepType.BUDGET_SLIDER,
            isRequired = true
        ),
        PlanningStep(
            id = "interests",
            question = "🎯 What interests you most?",
            type = com.example.odyssey.data.models.StepType.MULTIPLE_CHOICE,
            options = listOf(
                "🏛️ Historical Sites", "🍽️ Food & Culture", "🎢 Adventure",
                "🏖️ Relaxation", "🛍️ Shopping", "🌃 Nightlife",
                "🎭 Arts & Culture", "🌿 Nature"
            ),
            isRequired = true
        ),
        PlanningStep(
            id = "travel_style",
            question = "✈️ What's your travel style?",
            type = com.example.odyssey.data.models.StepType.SINGLE_CHOICE,
            options = listOf("🎒 Backpacker", "🏨 Comfort", "✨ Luxury", "👨‍👩‍👧‍👦 Family"),
            isRequired = true
        ),
        PlanningStep(
            id = "accommodation",
            question = "🏠 Where would you like to stay?",
            type = com.example.odyssey.data.models.StepType.SINGLE_CHOICE,
            options = listOf("🏨 Hotel", "🏠 Airbnb", "🏕️ Hostel", "🌴 Resort"),
            isRequired = true
        )
    )
}