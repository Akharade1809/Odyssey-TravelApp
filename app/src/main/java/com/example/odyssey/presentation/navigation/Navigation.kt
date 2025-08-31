package com.example.odyssey.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.odyssey.presentation.ui.home.HomeScreen
import com.example.odyssey.presentation.ui.home.HomeViewModel
import com.example.odyssey.presentation.ui.tripPlanning.TripPlanningScreen
import com.example.odyssey.presentation.ui.tripPlanning.TripPlanningViewModel
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(
    modifier: Modifier,
    navController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            val homeVM : HomeViewModel = koinViewModel()
            HomeScreen(
                navController = navController,
                viewModel = homeVM
            )
        }

        composable("trip-planning"){
            val tripPlanningVM : TripPlanningViewModel = koinViewModel()
            TripPlanningScreen(
                viewModel = tripPlanningVM
            )
        }

    }
}