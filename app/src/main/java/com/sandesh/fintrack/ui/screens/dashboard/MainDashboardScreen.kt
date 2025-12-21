package com.sandesh.fintrack.ui.screens.dashboard

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
import com.sandesh.fintrack.ui.screens.analytics.AnalyticsScreen
import com.sandesh.fintrack.ui.screens.settings.SettingsScreen
import com.sandesh.fintrack.ui.screens.transaction.transaction.TransactionsScreen
import com.sandesh.fintrack.ui.screens.transaction.transaction.TransactionsViewModel

@Composable
fun MainDashboardScreen(name: String) {

    // Selected tab state
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selected = selectedTab,
                onChange = { selectedTab = it }
            )
        },
        containerColor = Color(0xFF0D0B21)
    ) {

        // Change screens based on selected tab
        when (selectedTab) {
            0 -> DashboardScreen(Modifier.padding(it), name)
            1 -> {
                val txnViewModel: TransactionsViewModel = viewModel()
                TransactionsScreen(
                    viewModel = txnViewModel,
                    onAddClick = {}
                )
            }

            2 -> AnalyticsScreen()
            3 -> SettingsScreen()
        }
    }
}
