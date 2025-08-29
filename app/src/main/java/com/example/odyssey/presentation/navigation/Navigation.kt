package com.example.odyssey.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.odyssey.presentation.ui.home.HomeScreen
import com.example.odyssey.presentation.ui.home.HomeViewModel
import org.koin.androidx.compose.koinViewModel

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
                viewModel = homeVM
            )
        }

    }
}