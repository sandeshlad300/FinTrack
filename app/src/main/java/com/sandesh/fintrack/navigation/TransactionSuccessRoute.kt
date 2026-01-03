package com.sandesh.fintrack.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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

    val viewModel: TransactionSuccessViewModel = viewModel(
        factory = TransactionSuccessViewModelFactory(
            transactionId,
            repository
        )
    )

    TransactionSuccessScreen(
        viewModel = viewModel,
        onAddAnotherTransaction = {
            navController.navigate(Screen.Dashboard.route)
        },
        onViewAllTransactions = {
          //  navController.navigate(Screen.Transactions.route)
            navController.navigate(Screen.Dashboard.route + "?tab=1") {
                popUpTo(Screen.Dashboard.route) { inclusive = true }
            }
        }
    )
}
