package com.sandesh.fintrack.ui.screens.dashboard

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sandesh.fintrack.core.data.AppPreferences
import com.sandesh.fintrack.domain.TransactionRepository
import com.sandesh.fintrack.domain.TransactionRepositoryImpl
import com.sandesh.fintrack.ui.screens.dashboard.recentTransaction.RecentTransactionSection
import com.sandesh.fintrack.ui.screens.transaction.transaction.TransactionsViewModel
import com.sandesh.fintrack.ui.screens.transaction.transaction.TransactionsViewModelFactory

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    repository: TransactionRepository,
    onAddTransactionClick: () -> Unit
) {

    val context = LocalContext.current
    val appPreferences = remember {
        AppPreferences.getInstance(context)
    }
    val name by appPreferences.userName.collectAsState(initial = "User")


    val viewModel: TransactionsViewModel = viewModel(
        factory = TransactionsViewModelFactory(repository)
    )
    val dashboardViewModel: DashboardViewModel = viewModel(
        factory = DashboardViewModelFactory(repository as TransactionRepositoryImpl)
    )

    val state by viewModel.state.collectAsState()

    val income by dashboardViewModel.income.collectAsState()
    val expense by dashboardViewModel.expense.collectAsState()
    val total by dashboardViewModel.totalBalance.collectAsState()


    val recentTransactions = remember(
        state.todayList,
        state.yesterdayList
    ) {
        (state.todayList + state.yesterdayList)
            .take(3)
    }


    Scaffold { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0D0B21))
                .padding(innerPadding)
                .padding(horizontal = 18.dp)
        ) {

            GreetingHeader(name = name)
            Spacer(Modifier.height(20.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .windowInsetsPadding(WindowInsets.navigationBars),
                verticalArrangement = Arrangement.spacedBy(26.dp)
            ) {
                item {
                    BalanceCard(
                        totalBalance = total,
                        income = income,
                        expense = expense,
                    )

                }

                item {
                    QuickActionsSection(
                        onAddTransactionClick = onAddTransactionClick
                    )
                }

                item {
                    if (recentTransactions.isNotEmpty()) {
                        RecentTransactionSection(
                            transactions = recentTransactions
                        )
                    }
                 }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

        }
    }
}
