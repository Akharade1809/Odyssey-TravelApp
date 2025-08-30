package com.example.odyssey.presentation.ui.home

import android.Manifest
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.odyssey.data.models.Destination
import com.example.odyssey.presentation.theme.*
import com.example.odyssey.presentation.ui.components.AICuratedDestinationsSection
import com.example.odyssey.presentation.ui.components.AIQuickActionsSection
import com.example.odyssey.presentation.ui.components.AITravelInsightsCard
import com.example.odyssey.presentation.ui.components.AIWelcomeHeaderWithLocation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    onNavigateToItinerary: () -> Unit = {},
    onNavigateToChat: () -> Unit = {},
    onNavigateToExplore: () -> Unit = {},
    onNavigateToDestination: (Destination) -> Unit = {},
    viewModel: HomeViewModel
) {
    val state by viewModel.viewState.collectAsState()
    val context = LocalContext.current

    val locationPermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(locationPermissionState.allPermissionsGranted) {
        if (locationPermissionState.allPermissionsGranted) {
            viewModel.handleIntent(HomeIntent.LoadHomeData)
        } else {
            // Load data without location first
            viewModel.handleIntent(HomeIntent.LoadHomeData)
            // Then request permissions
            locationPermissionState.launchMultiplePermissionRequest()
        }
    }

    // Handle permission grant after user responds
    LaunchedEffect(locationPermissionState.permissions.any { it.status.isGranted }) {
        if (locationPermissionState.permissions.any { it.status.isGranted }) {
            viewModel.handleIntent(HomeIntent.LoadLocationBasedData)
        }
    }
    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.NavigateToItinerary -> onNavigateToItinerary()
                is HomeEffect.NavigateToChat -> onNavigateToChat()
                is HomeEffect.NavigateToDestination -> onNavigateToDestination(effect.destination)
                is HomeEffect.NavigateToExplore -> onNavigateToExplore()
                is HomeEffect.ShowError -> {
                    // Handle error with snackbar or toast
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
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
                    )
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            // AI-Powered Welcome Header
            item {
                AIWelcomeHeaderWithLocation(
                    userName = state.userPreferences?.userName?.capitalize() ?: "Explorer",
                    isLoading = state.isLoading,
                    locationStatus = state.locationStatus ?: "Welcome back!",
                    hasLocationData = state.hasLocationData,
                    onLocationClick = {
                        if (!locationPermissionState.allPermissionsGranted) {
                            locationPermissionState.launchMultiplePermissionRequest()
                        }
                    },
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            // AI Quick Actions
            item {
                AIQuickActionsSection(
                    actions = state.quickActions,
                    onActionClick = { action ->
                        viewModel.handleIntent(HomeIntent.OnQuickActionClick(action))
                    },
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            Log.d("Destination", "HomeScreen: ${state.popularDestinations} ")
            // AI-Curated Destinations
            item {
                AICuratedDestinationsSection(
                    destinations = state.popularDestinations,
                    isLoading = state.isLoading,
                    onDestinationClick = { destination ->
                        viewModel.handleIntent(HomeIntent.OnDestinationClick(destination))
                    },
                    onSeeAllClick = onNavigateToExplore
                )
            }

            // AI Travel Insights
            item {
                AITravelInsightsCard(
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            // Spacer for bottom padding
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        // Error handling
        state.error?.let { error ->
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = ErrorRed),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = error,
                    modifier = Modifier.padding(16.dp),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}



