package com.sandesh.fintrack.navigation

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.sandesh.fintrack.domain.TransactionRepositoryImpl
import com.sandesh.fintrack.ui.screens.auth.RegistrationScreen
import com.sandesh.fintrack.ui.screens.auth.RegistrationViewModel
import com.sandesh.fintrack.ui.screens.dashboard.MainDashboardScreen
import com.sandesh.fintrack.ui.screens.intro.IntroScreen
import com.sandesh.fintrack.ui.screens.intro.IntroViewModel
import com.sandesh.fintrack.ui.screens.intro.IntroViewModelFactory
import com.sandesh.fintrack.ui.screens.intro.introPages
import com.sandesh.fintrack.ui.screens.splash.SplashScreen
import com.sandesh.fintrack.ui.screens.splash.SplashViewModel
import com.sandesh.fintrack.ui.screens.splash.SplashViewModelFactory
import com.sandesh.fintrack.ui.screens.transaction.addTransaction.AddTransactionScreen
import com.sandesh.fintrack.ui.screens.transaction.room.AppDatabase


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(
    navController: NavHostController
) {

    val context = LocalContext.current
    val database = remember { AppDatabase.getInstance(context) }
    val repository = remember { TransactionRepositoryImpl(database.transactionDao()) }

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {


        composable(Screen.Splash.route) { backStackEntry ->
            val context = LocalContext.current
            val firstLaunchStore = remember { FirstLaunchStore(context) }

            val splashViewModel: SplashViewModel = viewModel(
                backStackEntry,
                factory = SplashViewModelFactory(firstLaunchStore)
            )

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
            arguments = listOf(
                navArgument("name") { type = NavType.StringType }
            )
        ) { entry ->

            val name = entry.arguments?.getString("name") ?: "User"

            //PASS navController
            MainDashboardScreen(
                name = name,
                navController = navController,
                onAddClick = {
                    navController.navigate(Screen.AddTransactions.route)
                }
            )
        }


        // ADD TRANSACTION DESTINATION
        composable(Screen.AddTransactions.route) {
            AddTransactionScreen(
                navController = navController,
                repository = repository,
                onBack = { navController.popBackStack() }
            )
        }

// SUCCESS DESTINATION
        composable(
            route = "transaction_success/{transactionId}",
            arguments = listOf(navArgument("transactionId") { type = NavType.LongType })
        ) { backStackEntry ->
            val transactionId = backStackEntry.arguments!!.getLong("transactionId")
            TransactionSuccessRoute(
                navController = navController,
                repository = repository,
                transactionId = transactionId
            )
        }
    }

    }
