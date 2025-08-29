package com.example.odyssey.presentation.ui.home

import androidx.compose.ui.graphics.Color
import com.example.odyssey.core.base.ViewEffect
import com.example.odyssey.core.base.ViewIntent
import com.example.odyssey.core.base.ViewState
import com.example.odyssey.data.models.Destination
import com.example.odyssey.data.models.UserPreferences

data class QuickAction(
    val id : String,
    val title : String,
    val subtitle: String,
    val icon: Int,
    val color: Color
)

fun getDefaultQuickActions(): List<QuickAction> = listOf(
    QuickAction("plan_trip", "Plan Trip", "Create smart itinerary", android.R.drawable.ic_menu_compass,Color.Blue),
    QuickAction("ai_chat", "AI Assistant", "Get travel advice", android.R.drawable.ic_menu_help, Color.Green),
    QuickAction("explore", "Explore", "Discover destinations", android.R.drawable.ic_menu_search, Color.Yellow),
    QuickAction("expenses", "Track Expenses", "Manage your budget", android.R.drawable.ic_menu_agenda, Color.Red)
)

data class HomeState(
    val isLoading : Boolean = false,
    val popularDestinations : List<Destination> = emptyList(),
    val userPreferences : UserPreferences? = null,
    val quickActions : List<QuickAction> = getDefaultQuickActions(),
    val error : String? = null
) : ViewState

sealed class HomeIntent : ViewIntent {
    object LoadHomeData : HomeIntent()
    object NavigateToItinerary : HomeIntent()
    object NavigateToChat : HomeIntent()
    object NavigateToExplore : HomeIntent()
    data class OnQuickActionClick(val action: QuickAction) : HomeIntent()
    data class OnDestinationClick(val destination: Destination) : HomeIntent()
}

sealed class HomeEffect : ViewEffect {
    object NavigateToItinerary : HomeEffect()
    object NavigateToChat : HomeEffect()
    object NavigateToExplore : HomeEffect()
    data class NavigateToDestination(val destination: Destination) : HomeEffect()
    data class ShowError(val message: String) : HomeEffect()
}

