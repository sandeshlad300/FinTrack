package com.sandesh.fintrack.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    name: String
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0D0B21))
                .padding(horizontal = 18.dp)
                .padding(innerPadding)
        ) {
            GreetingHeader(name = name)
            Spacer(Modifier.height(20.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 20.dp, top = 0.dp),
                verticalArrangement = Arrangement.spacedBy(26.dp)
            ) {
                item { BalanceCard() }

                item {
                    QuickActionsSection()
                }

                item {
                    RecentTransactionSection()
                }

                item {
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}
