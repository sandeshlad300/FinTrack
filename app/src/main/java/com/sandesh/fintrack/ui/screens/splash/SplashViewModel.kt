package com.sandesh.fintrack.ui.screens.splash


import com.sandesh.fintrack.core.mvi.BaseViewModel
import kotlinx.coroutines.delay


class SplashViewModel :
    BaseViewModel<SplashState, SplashEvent, SplashEffect>() {

    override fun createInitialState() = SplashState()

    override suspend fun handleEvent(event: SplashEvent) {
        if (event == SplashEvent.Start) {
            delay(2000)
            sendEffect { SplashEffect.NavigateToIntro }
        }
    }
}
