package com.example.odyssey.presentation.ui.home

import androidx.lifecycle.viewModelScope
import com.example.odyssey.core.base.BaseViewModel
import com.example.odyssey.domain.usecases.GetDestinationByCategory
import com.example.odyssey.domain.usecases.GetPopularDestinationUseCase
import com.example.odyssey.domain.usecases.GetUserPreferencesUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPopularDestinationUseCase: GetPopularDestinationUseCase,
    private val getDestinationByCategory: GetDestinationByCategory,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase
) : BaseViewModel<HomeIntent, HomeState, HomeEffect>() {
    override fun setInitialState(): HomeState  = HomeState()

    override fun handleIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.LoadHomeData -> loadHomeData()
            HomeIntent.NavigateToChat -> setEffect(HomeEffect.NavigateToChat)
            HomeIntent.NavigateToExplore -> setEffect(HomeEffect.NavigateToExplore)
            HomeIntent.NavigateToItinerary -> setEffect(HomeEffect.NavigateToItinerary)
            is HomeIntent.OnDestinationClick -> setEffect(HomeEffect.NavigateToDestination(intent.destination))
            is HomeIntent.OnQuickActionClick -> handleQuickActions(intent.action)
        }
    }

    private fun loadHomeData(){
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }

            try {
                getPopularDestinationUseCase()
                    .catch{ error ->
                        setState { copy(isLoading = false, error = error.message) }
                    }
                    .collect { destinations ->
                        setState { copy(isLoading = false, popularDestinations = destinations) }
                    }

                getUserPreferencesUseCase()
                    .catch {  error ->
//                            setState { copy(isLoading = false, error = error.message) }
                    }
                    .collect { userPreferences ->
                        setState { copy(userPreferences = userPreferences) }
                    }
            } catch (e : Exception){
                setState {
                    copy(isLoading = false, error = e.message ?: "Unknown error occurred")
                }
            }
        }
    }

    private fun handleQuickActions(action: QuickAction){
        when(action.id){
            "plan_trip" -> setEffect(HomeEffect.NavigateToItinerary)
            "ai_chat" -> setEffect(HomeEffect.NavigateToChat)
            "explore" -> setEffect(HomeEffect.NavigateToExplore)
            "expenses" -> {
                setEffect(HomeEffect.ShowError("Expenses feature coming soon...!"))
            }
        }

    }
}