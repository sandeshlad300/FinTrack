package com.sandesh.fintrack.ui.screens.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandesh.fintrack.core.data.FirstLaunchStore
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class IntroViewModel(
    private val markFinished: suspend () -> Unit,
    initialTotalPages: Int = 3
) : ViewModel() {


    private val _state = MutableStateFlow(IntroState(totalPages = initialTotalPages))
    val state: StateFlow<IntroState> = _state.asStateFlow()


    private val _effects = Channel<IntroEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()


    fun onEvent(event: IntroEvent) {
        when (event) {
            IntroEvent.Next -> next()
            IntroEvent.Back -> back()
            IntroEvent.Skip -> skip()
            IntroEvent.Finish -> finish()
            is IntroEvent.PageChanged -> _state.value = _state.value.copy(currentPage = event.index)
        }
    }


    private fun next() {
        val s = _state.value
        if (s.currentPage + 1 >= s.totalPages) finish() else _state.value =
            s.copy(currentPage = s.currentPage + 1)
    }


    private fun back() {
        _state.value =
            _state.value.copy(currentPage = (_state.value.currentPage - 1).coerceAtLeast(0))
    }


    private fun skip() {
        finish()
    }


    private fun finish() {
        viewModelScope.launch {
            try {
                markFinished()
            } catch (t: Throwable) {
                t.message ?: "Something went wrong!"
            }
            _state.value = _state.value.copy(isFinished = true)
            _effects.send(IntroEffect.NavigateToLogin)
        }
    }
}

