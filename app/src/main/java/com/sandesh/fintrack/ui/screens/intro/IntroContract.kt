package com.sandesh.fintrack.ui.screens.intro

sealed interface IntroEvent {
    object Next : IntroEvent
    object Back : IntroEvent
    object Skip : IntroEvent
    object Finish : IntroEvent
    data class PageChanged(val index: Int) : IntroEvent
}


data class IntroState(
    val currentPage: Int = 0,
    val totalPages: Int = 3,
    val isFinished: Boolean = false
)


sealed interface IntroEffect {
    object NavigateToLogin : IntroEffect
}