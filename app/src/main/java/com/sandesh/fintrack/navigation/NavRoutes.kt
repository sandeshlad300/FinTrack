package com.sandesh.fintrack.navigation


sealed class Screen(val route: String) {

    object Splash : Screen("splash")
   /* object Dashboard : Screen("dashboard/{name}") {
        fun passName(name: String): String = "dashboard/$name"
    }*/

    object Dashboard : Screen("dashboard/{name}?tab={tab}") {

        fun passName(name: String): String {
            return "dashboard/$name?tab=0"
        }

        fun withTab(name: String, tab: Int): String {
            return "dashboard/$name?tab=$tab"
        }
    }

    object Intro : Screen("intro")
    object Registration : Screen("registration")
    object Transactions : Screen("transactions")
    object AddTransactions : Screen("add_transactions")
    object TransactionSuccess : Screen("transaction_success/{transactionId}") {
        fun createRoute(transactionId: Long) =
            "transaction_success/$transactionId"
    }
    object Analytics : Screen("analytics")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
}
