package com.sandesh.fintrack.ui.screens.intro

import androidx.annotation.RawRes
import com.sandesh.fintrack.R

data class IntroPage(
    val title: String,
    val subtitle: String,
    @RawRes val lottieRes: Int,
    val gradientColors: List<Long>
)

val introPages = listOf(
    IntroPage(
        title = "Smart Expense Tracking",
        subtitle = "Track your daily spend seamlessly",
        lottieRes = R.raw.intro_finance,
        gradientColors = listOf(
            0xFF004E92, 0xFF000428
        )
    ),
    IntroPage(
        title = "Plan & Achieve Goals",
        subtitle = "Set budgets and reach milestones easily",
        lottieRes = R.raw.money,
        gradientColors = listOf(
            0xFF3A1C71, 0xFFD76D77, 0xFFFFAF7B
        )
    ),
    IntroPage(
        title = "AI Insights",
        subtitle = "Get personalized spending insights",
        lottieRes = R.raw.finance_report,
        gradientColors = listOf(
            0xFF009FFF, 0xFFec2F4B
        )
    )
)
