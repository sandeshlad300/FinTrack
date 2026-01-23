package com.sandesh.fintrack.ui.screens.transaction.addTransaction

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sandesh.fintrack.common.AnimatedTabRow
import com.sandesh.fintrack.common.GradientButton
import com.sandesh.fintrack.common.transaction.AmountInput
import com.sandesh.fintrack.common.transaction.CategoryBottomSheet
import com.sandesh.fintrack.common.transaction.CommonDropdown
import com.sandesh.fintrack.common.transaction.DateField
import com.sandesh.fintrack.common.transaction.DatePickerHandler
import com.sandesh.fintrack.common.transaction.NoteField
import com.sandesh.fintrack.common.transaction.SectionDivider
import com.sandesh.fintrack.domain.TransactionRepository
import com.sandesh.fintrack.navigation.Screen
import com.sandesh.fintrack.ui.theme.PrimaryBlue
import com.sandesh.fintrack.ui.theme.TealAccent
import java.text.DecimalFormat


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    navController: NavController,
    repository: TransactionRepository,
    onBack: () -> Unit
) {

    val viewModel: AddTransactionViewModel = viewModel(
        factory = AddTransactionViewModelFactory(repository)
    )

    val state by viewModel.state.collectAsState()
    var showDatePicker by remember { mutableStateOf(false) }

    BackHandler {
        navController.navigate(Screen.Dashboard.route) {
            popUpTo(Screen.Dashboard.route) { inclusive = false }
            launchSingleTop = true
        }
    }


    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )


    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AddTransactionEffect.NavigateToSuccess -> {
                    navController.navigate(
                        Screen.TransactionSuccess.createRoute(effect.transactionId)
                    ) {
                        popUpTo(Screen.AddTransactions.route) { inclusive = true }
                    }
                }
                AddTransactionEffect.NavigateBack -> onBack()
                AddTransactionEffect.OpenDatePicker -> {
                    showDatePicker = true
                }
            }
        }
    }




    if (showDatePicker) {
        DatePickerHandler(
            onDateSelected = { selectedDate ->
                viewModel.onIntent(
                    AddTransactionIntent.DateSelected(selectedDate)
                )
            },
                    onDismiss = {
                showDatePicker = false
            }
        )
    }


    if (state.showCategorySheet) {
        ModalBottomSheet(
            onDismissRequest = {
                viewModel.onIntent(AddTransactionIntent.DismissCategorySheet)
            },
            sheetState = sheetState,
            containerColor = Color(0xFF16142B),
            shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp),
            dragHandle = null
        ) {
            CategoryBottomSheet(
                categories = state.availableCategories,
                onCategorySelected = {
                    viewModel.onIntent(
                        AddTransactionIntent.CategorySelected(it)
                    )
                }
            )
        }
    }



    Scaffold(
        containerColor = Color(0xFF0D0B21),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0D0B21))
                    .statusBarsPadding()
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Add Transaction",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.navigate(Screen.Dashboard.route)
                            }
                        ) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )

                // subtle divider
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.White.copy(alpha = 0.05f))
                        .align(Alignment.BottomCenter)
                )
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(16.dp)
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(14.dp),
                        ambientColor = PrimaryBlue.copy(alpha = 0.35f),
                        spotColor = TealAccent.copy(alpha = 0.35f)
                    )
            ) {
                GradientButton(
                    text = if (state.isLoading) "Saving..." else "Save Transaction",
                    enabled = state.isFormValid && !state.isLoading,
                    loading = state.isLoading,
                    onClick = {
                        viewModel.onIntent(AddTransactionIntent.SaveClicked)
                    }
                )

            }
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0D0B21))
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {

            Column {

                Spacer(Modifier.height(20.dp))

                // Income / Expense toggle
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedTabRow(
                        tabs = listOf("Income", "Expense"),
                        selectedTab = if (state.isIncome) 0 else 1,
                        onTabSelected = {
                            viewModel.onIntent(
                                AddTransactionIntent.TransactionTypeChanged(it == 0)
                            )
                        }
                    )
                }

                Spacer(Modifier.height(24.dp))

                // MAIN CARD (Depth)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFF16142B),
                            shape = RoundedCornerShape(22.dp)
                        )
                        .padding(20.dp)
                ) {

                    // HERO AMOUNT
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.verticalGradient(
                                    listOf(
                                        Color(0xFF1C1950),
                                        Color(0xFF16142B)
                                    )
                                ),
                                shape = RoundedCornerShape(18.dp)
                            )
                            .padding(vertical = 26.dp)
                    ) {
                        AmountInput(
                            rawAmount = state.amount,
                            isInvalid = state.showAmountError,
                            onAmountChange = {
                                viewModel.onIntent(
                                    AddTransactionIntent.AmountChanged(it)
                                )
                            }
                        )
                    }

                    SectionDivider()

                    CommonDropdown(
                        label = "Category",
                        value = state.category.ifBlank { "" },
                        placeholder = "Select category",
                        onClick = {
                            viewModel.onIntent(AddTransactionIntent.CategoryClicked)
                        }
                    )

                    SectionDivider()

                    DateField(
                        date = state.date,
                        onClick = {
                            viewModel.onIntent(AddTransactionIntent.DateClicked)
                        }
                    )


                    SectionDivider()

                    NoteField(
                        note = state.note,
                        onValueChange = {
                            viewModel.onIntent(
                                AddTransactionIntent.NoteChanged(it)
                            )
                        }
                    )
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}



fun formatAmount(input: String): String {
    if (input.isBlank()) return ""

    return try {
        val number = input.toDouble()
        val formatter = DecimalFormat("#,##0.00")
        formatter.format(number)
    } catch (e: Exception) {
        input
    }
}

