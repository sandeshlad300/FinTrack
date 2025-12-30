package com.sandesh.fintrack.ui.screens.splash


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sandesh.fintrack.core.data.FirstLaunchStore

class SplashViewModelFactory(
    private val firstLaunchStore: FirstLaunchStore
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SplashViewModel(firstLaunchStore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
