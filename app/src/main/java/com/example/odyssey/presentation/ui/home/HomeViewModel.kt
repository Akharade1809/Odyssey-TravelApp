package com.example.odyssey.presentation.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.odyssey.core.base.BaseViewModel
import com.example.odyssey.core.location.LocationManager
import com.example.odyssey.domain.usecases.GetDestinationByCategory
import com.example.odyssey.domain.usecases.GetPopularDestinationUseCase
import com.example.odyssey.domain.usecases.GetUserPreferencesUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPopularDestinationUseCase: GetPopularDestinationUseCase,
    private val getDestinationByCategory: GetDestinationByCategory,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    private val locationManager: LocationManager // Inject LocationManager
) : BaseViewModel<HomeIntent, HomeState, HomeEffect>() {

    override fun setInitialState(): HomeState = HomeState()

    override fun handleIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.LoadHomeData -> loadHomeData()
            HomeIntent.LoadLocationBasedData -> loadLocationBasedData()
            HomeIntent.NavigateToChat -> setEffect(HomeEffect.NavigateToChat)
            HomeIntent.NavigateToExplore -> setEffect(HomeEffect.NavigateToExplore)
            HomeIntent.NavigateToItinerary -> setEffect(HomeEffect.NavigateToItinerary)
            is HomeIntent.OnDestinationClick -> setEffect(HomeEffect.NavigateToDestination(intent.destination))
            is HomeIntent.OnQuickActionClick -> handleQuickActions(intent.action)
        }
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            setState { copy(isLoading = true, error = null) }

            try {
                // Load user preferences first
                getUserPreferencesUseCase()
                    .catch { /* Silently handle preference errors */ }
                    .collect { userPreferences ->
                        setState { copy(userPreferences = userPreferences) }
                    }

                // Load initial popular destinations (without location)
                getPopularDestinationUseCase()
                    .catch { error ->
                        setState { copy(isLoading = false, error = error.message) }
                    }
                    .collect { destinations ->
                        setState {
                            copy(
                                isLoading = false,
                                popularDestinations = destinations,
                                locationStatus = "Loading nearby recommendations..."
                            )
                        }
                    }

                // Then try to get location-based recommendations
                loadLocationBasedData()

            } catch (e: Exception) {
                setState {
                    copy(isLoading = false, error = e.message ?: "Unknown error occurred")
                }
            }
        }
    }

    private fun loadLocationBasedData() {
        viewModelScope.launch {
            try {
                setState { copy(locationStatus = "Getting your location...") }

                val location = locationManager.getCurrentLocation()

                if (location != null) {
                    setState { copy(locationStatus = "Finding amazing places near you...") }

                    // Fetch location-based popular destinations
                    getPopularDestinationUseCase(location.latitude, location.longitude)
                        .catch { error ->
                            setState {
                                copy(
                                    locationStatus = "Using general recommendations",
                                    error = null // Don't show error, just fallback
                                )
                            }
                        }
                        .collect { destinations ->
                            Log.d("HomeViewModel", "loadLocationBasedData: destinations: $destinations")
                            setState {
                                copy(
                                    isLoading = false,
                                    popularDestinations = destinations,
                                    locationStatus = "Showing places near you",
                                    hasLocationData = true
                                )
                            }
                        }
                } else {
                    setState {
                        copy(
                            locationStatus = "Enable location for personalized recommendations",
                            hasLocationData = false
                        )
                    }
                }
            } catch (e: Exception) {
                setState {
                    copy(
                        locationStatus = "Using general recommendations",
                        hasLocationData = false
                    )
                }
            }
        }
    }

    private fun handleQuickActions(action: QuickAction) {
        when (action.id) {
            "plan_trip" -> setEffect(HomeEffect.NavigateToItinerary)
            "ai_chat" -> setEffect(HomeEffect.NavigateToChat)
            "explore" -> setEffect(HomeEffect.NavigateToExplore)
            "expenses" -> {
                setEffect(HomeEffect.ShowError("Expenses feature coming soon...!"))
            }
        }
    }
}
