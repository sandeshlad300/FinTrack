package com.sandesh.fintrack.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween

val defaultTransition: AnimatedContentTransitionScope<*>.() -> ContentTransform
    get() = {
        slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Left,
            animationSpec = tween(300)
        ) togetherWith slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Left,
            animationSpec = tween(300)
        )
    }
