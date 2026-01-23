package com.sandesh.fintrack.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sandesh.fintrack.core.data.AppPreferences
import com.sandesh.fintrack.domain.TransactionRepository
import com.sandesh.fintrack.ui.screens.transaction.transactionSuccess.TransactionSuccessScreen
import com.sandesh.fintrack.ui.screens.transaction.transactionSuccess.TransactionSuccessViewModel
import com.sandesh.fintrack.ui.screens.transaction.transactionSuccess.TransactionSuccessViewModelFactory

@Composable
fun TransactionSuccessRoute(
    navController: NavController,
    repository: TransactionRepository,
    transactionId: Long
) {

    val context = LocalContext.current
    val appPreferences = remember {
        AppPreferences.getInstance(context)
    }

    val name by appPreferences.userName.collectAsState(initial = "User")


    val viewModel: TransactionSuccessViewModel = viewModel(
        factory = TransactionSuccessViewModelFactory(
            transactionId,
            repository
        )
    )


    TransactionSuccessScreen(
        navController = navController,
        viewModel = viewModel,
        onAddAnotherTransaction = {
            navController.navigate(
                Screen.Dashboard.passName(name)
            ) {
                popUpTo(Screen.Dashboard.route.substringBefore("?")) {
                    inclusive = true
                }
            }
        },
        onViewAllTransactions = {
            navController.navigate(
                Screen.Dashboard.withTab(name, 1)
            ) {
                popUpTo(Screen.Dashboard.route.substringBefore("?")) {
                    inclusive = true
                }
            }
        }
    )

}
