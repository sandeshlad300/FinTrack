package com.sandesh.fintrack.ui.screens.dashboard

import android.R.attr.padding
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.sandesh.fintrack.navigation.Screen
import com.sandesh.fintrack.ui.screens.analytics.AnalyticsScreen
import com.sandesh.fintrack.ui.screens.settings.SettingsScreen
import com.sandesh.fintrack.ui.screens.transaction.transaction.TransactionsScreen
import com.sandesh.fintrack.ui.screens.transaction.transaction.TransactionsViewModel

@Composable
fun MainDashboardScreen(
    name: String,
    navController: NavHostController
) {

    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selected = selectedTab,
                onChange = { selectedTab = it }
            )
        },
        containerColor = Color(0xFF0D0B21)
    ) { padding ->

        when (selectedTab) {
            0 -> DashboardScreen(
                modifier = Modifier.padding(padding),
                name = name,
                onAddTransactionClick = {
                    navController.navigate(Screen.AddTransactions.route) // 🔥 navigation
                }
            )

            1 -> TransactionsScreen(
                viewModel = viewModel(),
                onAddClick = {
                    navController.navigate(Screen.Transactions.route)
                }
            )

            2 -> AnalyticsScreen()
            3 -> SettingsScreen()
        }
    }
}
