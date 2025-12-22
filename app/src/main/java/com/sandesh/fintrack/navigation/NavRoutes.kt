package com.sandesh.fintrack.navigation


sealed class Screen(val route: String) {

    object Splash : Screen("splash")
    object Dashboard : Screen("dashboard/{name}") {
        fun passName(name: String): String = "dashboard/$name"
    }
    object Intro : Screen("intro")
    object Registration : Screen("registration")
    object Transactions : Screen("transactions")
    object AddTransactions : Screen("add_transactions")
    object Analytics : Screen("analytics")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
}
