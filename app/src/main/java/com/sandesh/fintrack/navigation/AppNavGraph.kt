package com.sandesh.fintrack.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sandesh.fintrack.core.data.FirstLaunchStore
import com.sandesh.fintrack.ui.screens.auth.RegistrationScreen
import com.sandesh.fintrack.ui.screens.auth.RegistrationViewModel
import com.sandesh.fintrack.ui.screens.dashboard.DashboardScreen
import com.sandesh.fintrack.ui.screens.dashboard.MainDashboardScreen
import com.sandesh.fintrack.ui.screens.intro.IntroScreen
import com.sandesh.fintrack.ui.screens.intro.IntroViewModel
import com.sandesh.fintrack.ui.screens.intro.IntroViewModelFactory
import com.sandesh.fintrack.ui.screens.intro.introPages
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

        composable(Screen.Intro.route) {
            val context = LocalContext.current
            val firstLaunchStore = remember { FirstLaunchStore(context) }
            val viewModel: IntroViewModel = viewModel(
                factory = IntroViewModelFactory(
                    markFinished = {
                        firstLaunchStore.setOnboardingShown(true)
                    },
                    totalPages = 3
                )
            )

            IntroScreen(
                pages = introPages,
                viewModel = viewModel,
                onNavigateToLogin = {
                    navController.navigate(Screen.Registration.route) {
                        popUpTo(Screen.Intro.route) { inclusive = true }
                    }
                }
            )

        }

        composable(Screen.Registration.route) { backStackEntry ->
            val registrationViewModel: RegistrationViewModel = viewModel(backStackEntry)

            RegistrationScreen(
                viewModel = registrationViewModel,
                onNavigateToDashboard = { name ->
                    navController.safeNavigate(
                        Screen.Dashboard.passName(name)
                    )
                }
            )
        }


        composable(
            route = Screen.Dashboard.route,
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { entry ->

            val name = entry.arguments?.getString("name") ?: "User"
            MainDashboardScreen(name = name)
        }


    }
}
