package com.sandesh.fintrack.navigation


sealed class Screen(val route: String) {

    object Splash : Screen("splash")
    object Home : Screen("Intro")
    object Intro : Screen("dashboard")
    object Transactions : Screen("transactions")
    object Analytics : Screen("analytics")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
}
