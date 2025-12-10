package com.sandesh.fintrack.navigation


sealed class Screen(val route: String) {

    object Splash : Screen("splash")
    object Dashboard : Screen("dashboard")
    object Intro : Screen("intro")
    object Registration : Screen("registration")
    object Transactions : Screen("transactions")
    object Analytics : Screen("analytics")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
}
