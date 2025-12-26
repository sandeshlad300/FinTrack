package com.sandesh.fintrack.ui.screens.transaction.transaction

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandesh.fintrack.common.transaction.FiltersRow
import com.sandesh.fintrack.common.transaction.SectionTitle
import com.sandesh.fintrack.common.transaction.TransactionCard


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsScreen(
    viewModel: TransactionsViewModel,
    onAddClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            if (effect is TransactionsEffect.NavigateToAddTransaction) {
                onAddClick()
            }
        }
    }

    Scaffold(
        containerColor = Color(0xFF0F172A),

        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Transactions",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Check your recent activity",
                            color = Color.Gray,
                            fontSize = 13.sp
                        )
                    }
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF1E293B))
                            .clickable { /* handle search */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0F172A)
                )
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddClick() },
                containerColor = Color(0xFF6366F1)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
            }
        },

        floatingActionButtonPosition = FabPosition.Center,

        ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            // FILTER CHIPS
            FiltersRow(
                selected = state.selectedFilter,
                onSelect = { filter ->
                    viewModel.onEvent(
                        TransactionsEvent.OnFilterChange(filter)
                    )
                }
            )



            Spacer(modifier = Modifier.height(16.dp))

            // LIST
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(
                    bottom = paddingValues.calculateBottomPadding() + 72.dp
                )

            ) {

                item { SectionTitle("TODAY") }
                items(state.todayList) { TransactionCard(it) }

                item { SectionTitle("YESTERDAY") }
                items(state.yesterdayList) { TransactionCard(it) }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}
