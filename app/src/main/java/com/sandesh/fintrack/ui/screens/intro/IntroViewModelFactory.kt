package com.sandesh.fintrack.ui.screens.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sandesh.fintrack.core.data.AppPreferences

class IntroViewModelFactory(
    private val appPrefs: AppPreferences,
    private val totalPages: Int = 3
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return IntroViewModel(
            appPrefs = appPrefs,
            initialTotalPages = totalPages
        ) as T
    }
}
