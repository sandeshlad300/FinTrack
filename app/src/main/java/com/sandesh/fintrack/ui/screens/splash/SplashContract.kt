package com.sandesh.fintrack.ui.screens.splash

import com.sandesh.fintrack.core.mvi.UiEffect
import com.sandesh.fintrack.core.mvi.UiEvent
import com.sandesh.fintrack.core.mvi.UiState


data class SplashState(
    val isLoading: Boolean = true
) : UiState

sealed class SplashEvent : UiEvent {
    object Start : SplashEvent()
}

sealed class SplashEffect : UiEffect {
    object NavigateToIntro : SplashEffect()
}
