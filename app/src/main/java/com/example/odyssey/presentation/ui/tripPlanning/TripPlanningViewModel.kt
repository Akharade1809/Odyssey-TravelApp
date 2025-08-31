package com.example.odyssey.presentation.ui.tripPlanning

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.odyssey.core.base.BaseViewModel
import com.example.odyssey.domain.entities.UserTripInput
import com.example.odyssey.domain.usecases.GenerateItineraryUseCase
import com.example.odyssey.domain.usecases.SaveUserTripPlanUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDate
import java.time.format.DateTimeFormatter

class TripPlanningViewModel(
    private val generateItineraryUseCase: GenerateItineraryUseCase,
    private val saveUserTripPlanUseCase: SaveUserTripPlanUseCase
) : BaseViewModel<TripPlanningIntent, TripPlanningState, TripPlanningEffect>() {
    override fun setInitialState(): TripPlanningState = TripPlanningState()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun handleIntent(intent: TripPlanningIntent) {
        when (intent) {
            is TripPlanningIntent.StartPlanning -> startPlanning()
            is TripPlanningIntent.AnswerCurrentStep -> answerCurrentStep(intent.answer)
            is TripPlanningIntent.NextStep -> moveToNextStep()
            is TripPlanningIntent.PreviousStep -> moveToPreviousStep()
            is TripPlanningIntent.GoToStep -> goToStep(intent.stepIndex)
            is TripPlanningIntent.GenerateItinerary -> generateItinerary()
            is TripPlanningIntent.SaveTripPlan -> saveTripPlan()
            is TripPlanningIntent.RestartPlanning -> restartPlanning()
        }
    }

    private fun startPlanning() {
        setState {
            copy(
                currentStepIndex = 0,
                planningSteps = getInitialPlanningSteps(),
                userInput = UserTripInput(),
                canGoBack = false,
                canProceedToNext = false
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun answerCurrentStep(answer: String) {
        val currentState = currentState
        val currentStep = currentState.planningSteps[currentState.currentStepIndex]

        val updatedSteps = currentState.planningSteps.toMutableList()
        updatedSteps[currentState.currentStepIndex] = currentStep.copy(
            userAnswer = answer,
            isCompleted = true
        )

        val updatedInput = updateUserInput(currentStep.id, answer, currentState.userInput)

        setState {
            copy(
                planningSteps = updatedSteps,
                userInput = updatedInput,
                canProceedToNext = true
            )
        }
    }

    private fun moveToNextStep() {
        val currentState = currentState
        if (currentState.currentStepIndex < currentState.planningSteps.size - 1) {
            setState {
                copy(
                    currentStepIndex = currentStepIndex + 1,
                    canGoBack = true,
                    canProceedToNext = false
                )
            }
        } else {
            generateItinerary()
        }
    }

    private fun moveToPreviousStep() {
        val currentState = currentState
        if (currentState.currentStepIndex > 0) {
            setState {
                copy(
                    currentStepIndex = currentStepIndex - 1,
                    canGoBack = currentStepIndex - 1 > 0,
                    canProceedToNext = true
                )
            }
        }
    }

    private fun goToStep(stepIndex: Int) {
        if (stepIndex >= 0 && stepIndex < currentState.planningSteps.size) {
            setState {
                copy(
                    currentStepIndex = stepIndex,
                    canGoBack = stepIndex > 0,
                    canProceedToNext = planningSteps[stepIndex].isCompleted
                )
            }
        }
    }

    private fun generateItinerary() {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }

            try {
                generateItineraryUseCase(currentState.userInput)
                    .catch { error ->
                        setState { copy(isLoading = false, error = error.message) }
                        setEffect(TripPlanningEffect.ShowError(error.message ?: "Failed to generate itinerary"))
                    }
                    .collect { result ->
                        result.fold(
                            onSuccess = { tripPlan ->
                                setState {
                                    copy(
                                        isLoading = false,
                                        generatedTripPlan = tripPlan,
                                        showSuccess = true
                                    )
                                }
                                setEffect(TripPlanningEffect.NavigateToGeneratedPlan)
                            },
                            onFailure = { error ->
                                setState { copy(isLoading = false, error = error.message) }
                                setEffect(TripPlanningEffect.ShowError(error.message ?: "Failed to generate itinerary"))
                            }
                        )
                    }
            } catch (e: Exception) {
                setState { copy(isLoading = false, error = e.message) }
                setEffect(TripPlanningEffect.ShowError(e.message ?: "Unknown error occurred"))
            }
        }
    }

    private fun saveTripPlan() {
        viewModelScope.launch {
            val tripPlan = currentState.generatedTripPlan
            if (tripPlan != null) {
                val saved = saveUserTripPlanUseCase(tripPlan)
                if (saved) {
                    setEffect(TripPlanningEffect.ShowSuccess("Trip plan saved successfully!"))
                } else {
                    setEffect(TripPlanningEffect.ShowError("Failed to save trip plan"))
                }
            }
        }
    }

    private fun restartPlanning() {
        setState {
            TripPlanningState().copy(
                currentStepIndex = 0,
                canGoBack = false
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateUserInput(stepId: String, answer: String, currentInput: UserTripInput): UserTripInput {
        return when (stepId) {
            "destination" -> currentInput.copy(destination = answer)
            "start_date" -> currentInput.copy(startDate = parseDate(answer))
            "end_date" -> currentInput.copy(endDate = parseDate(answer))
            "travelers" -> currentInput.copy(travelers = answer.toIntOrNull() ?: 1)
            "budget" -> currentInput.copy(budget = answer.toDoubleOrNull() ?: 1000.0)
            "interests" -> currentInput.copy(interests = answer.split(",").map { it.trim() })
            "travel_style" -> currentInput.copy(travelStyle = answer.lowercase())
            "accommodation" -> currentInput.copy(accommodationType = answer.lowercase())
            else -> currentInput
        }
    }

    // ✅ Always return kotlinx.datetime.LocalDate
    @RequiresApi(Build.VERSION_CODES.O)
    private fun parseDate(dateString: String): LocalDate? {
        return try {
            java.time.LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE)
                .toKotlinLocalDate() // convert to kotlinx.datetime.LocalDate
        } catch (e: Exception) {
            null
        }
    }
}
