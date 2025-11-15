package com.sandesh.fintrack.core.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : UiState, Event : UiEvent, Effect : UiEffect>
    : ViewModel() {

    protected abstract fun createInitialState(): State

    private val _uiState = MutableStateFlow(createInitialState())
    val uiState = _uiState.asStateFlow()

    private val _effect = Channel<Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun setState(reducer: State.() -> State) {
        _uiState.update { uiState.value.reducer() }
    }

    fun sendEffect(builder: () -> Effect) {
        viewModelScope.launch {
            _effect.send(builder())
        }
    }

    fun onEvent(event: Event) {
        viewModelScope.launch {
            handleEvent(event)
        }
    }

    protected abstract suspend fun handleEvent(event: Event)
}
