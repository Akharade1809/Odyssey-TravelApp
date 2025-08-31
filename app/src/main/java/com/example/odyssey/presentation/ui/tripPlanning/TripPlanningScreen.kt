package com.example.odyssey.presentation.ui.tripPlanning

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import com.example.odyssey.presentation.theme.*
import com.example.odyssey.presentation.ui.components.tripPlanning.GeneratingItineraryLoader
import com.example.odyssey.presentation.ui.components.tripPlanning.ItineraryGeneratedSuccess
import com.example.odyssey.presentation.ui.components.tripPlanning.PlanningStepContent
import com.example.odyssey.presentation.ui.components.tripPlanning.TripPlanningBottomBar
import com.example.odyssey.presentation.ui.components.tripPlanning.TripPlanningTopBar

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun TripPlanningScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateToGeneratedPlan: () -> Unit = {},
    viewModel: TripPlanningViewModel
) {
    val state by viewModel.viewState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.handleIntent(TripPlanningIntent.StartPlanning)
    }

    // Handle effects
    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is TripPlanningEffect.NavigateToGeneratedPlan -> onNavigateToGeneratedPlan()
                is TripPlanningEffect.NavigateBack -> onNavigateBack()
                is TripPlanningEffect.ShowError -> {
                    // Show error snackbar
                }
                is TripPlanningEffect.ShowSuccess -> {
                    // Show success message
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        TravelBlue.copy(alpha = 0.1f),
                        MaterialTheme.colorScheme.background,
                        AdventureOrange.copy(alpha = 0.05f)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top App Bar with Progress
            TripPlanningTopBar(
                currentStep = state.currentStepIndex + 1,
                totalSteps = state.planningSteps.size,
                onBackClick = {
                    if (state.canGoBack) {
                        viewModel.handleIntent(TripPlanningIntent.PreviousStep)
                    } else {
                        onNavigateBack()
                    }
                }
            )

            // Main Content
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                when {
                    state.isLoading -> {
                        GeneratingItineraryLoader(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    state.generatedTripPlan != null -> {
                        ItineraryGeneratedSuccess(
                            tripPlan = state.generatedTripPlan!!,
                            onSaveClick = {
                                viewModel.handleIntent(TripPlanningIntent.SaveTripPlan)
                            },
                            onViewDetailsClick = onNavigateToGeneratedPlan,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    else -> {
                        // Step-by-step planning
                        AnimatedContent(
                            targetState = state.currentStepIndex,
                            transitionSpec = {
                                slideInHorizontally(
                                    initialOffsetX = { if (targetState > initialState) 1000 else -1000 }
                                ) + fadeIn() with slideOutHorizontally(
                                    targetOffsetX = { if (targetState > initialState) -1000 else 1000 }
                                ) + fadeOut()
                            },
                            label = "step_transition"
                        ) { stepIndex ->
                            if (stepIndex < state.planningSteps.size) {
                                PlanningStepContent(
                                    step = state.planningSteps[stepIndex],
                                    stepNumber = stepIndex + 1,
                                    totalSteps = state.planningSteps.size,
                                    onAnswerSubmitted = { answer ->
                                        viewModel.handleIntent(TripPlanningIntent.AnswerCurrentStep(answer))
                                    },
                                    userInput = state.userInput,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }

            // Bottom Navigation
            if (!state.isLoading && state.generatedTripPlan == null) {
                TripPlanningBottomBar(
                    canGoBack = state.canGoBack,
                    canProceed = state.canProceedToNext,
                    isLastStep = state.currentStepIndex == state.planningSteps.size - 1,
                    onBackClick = {
                        viewModel.handleIntent(TripPlanningIntent.PreviousStep)
                    },
                    onNextClick = {
                        if (state.currentStepIndex == state.planningSteps.size - 1) {
                            viewModel.handleIntent(TripPlanningIntent.GenerateItinerary)
                        } else {
                            viewModel.handleIntent(TripPlanningIntent.NextStep)
                        }
                    },
                    modifier = Modifier
                            .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.ime)
                )
            }
        }
    }
}
