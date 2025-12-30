package com.sandesh.fintrack.ui.screens.splash


import com.sandesh.fintrack.core.data.FirstLaunchStore
import com.sandesh.fintrack.core.mvi.BaseViewModel
import kotlinx.coroutines.delay

class SplashViewModel(
    private val firstLaunchStore: FirstLaunchStore
) : BaseViewModel<SplashState, SplashEvent, SplashEffect>() {

    override fun createInitialState() = SplashState()

    override suspend fun handleEvent(event: SplashEvent) {
        when (event) {
            SplashEvent.Start -> loadSplash()
        }
    }

    private suspend fun loadSplash() {
        // 🔐 FIRST-INSTALL INITIALIZATION
        firstLaunchStore.initOnFirstLaunch()
        // Existing splash delay
        delay(2000)
        sendEffect { SplashEffect.NavigateToIntro }
    }
}
