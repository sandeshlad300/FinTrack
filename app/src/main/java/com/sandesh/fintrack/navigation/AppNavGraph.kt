package com.sandesh.fintrack.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sandesh.fintrack.ui.screens.intro.IntroScreen
import com.sandesh.fintrack.ui.screens.splash.SplashScreen
import com.sandesh.fintrack.ui.screens.splash.SplashViewModel


@Composable
fun AppNavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Splash.route) { backStackEntry ->
            val splashViewModel: SplashViewModel = viewModel(backStackEntry)
            SplashScreen(
                viewModel = splashViewModel,
                onNavigate = {
                    navController.safeNavigate(Screen.Intro.route)
                }
            )
        }

        composable(Screen.Intro.route) { IntroScreen() }
    }
}
