package com.sandesh.fintrack.ui.screens.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class IntroViewModelFactory(
    private val markFinished: suspend () -> Unit,
    private val totalPages: Int = 3
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IntroViewModel::class.java)) {
            return IntroViewModel(
                markFinished = markFinished,
                initialTotalPages = totalPages
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
