package com.sandesh.fintrack.navigation

import androidx.navigation.NavHostController


fun NavHostController.safeNavigate(
    route: String,
    popUpToRoute: String? = null,
    inclusive: Boolean = false
) {
    navigate(route) {
        launchSingleTop = true
        restoreState = true

        if (popUpToRoute != null) {
            popUpTo(popUpToRoute) {
                this.inclusive = inclusive
            }
        }
    }
}

