package com.sandesh.fintrack.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegistrationViewModel : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state

    private val _effect = Channel<AuthEffect>()
    val effect = _effect.receiveAsFlow()

    private val auth = FirebaseAuth.getInstance()

    fun onEvent(event: AuthEvent) {
        when (event) {

            is AuthEvent.EmailChanged -> {
                _state.value = _state.value.copy(email = event.value)
            }

            is AuthEvent.PasswordChanged -> {
                _state.value = _state.value.copy(password = event.value)
            }

            is AuthEvent.ConfirmPasswordChanged -> {
                _state.value = _state.value.copy(confirmPassword = event.value)
            }

            is AuthEvent.TogglePasswordVisibility -> {
                _state.value = _state.value.copy(
                    createPasswordVisible = !_state.value.createPasswordVisible
                )
            }

            is AuthEvent.ToggleConfirmPasswordVisibility -> {
                _state.value = _state.value.copy(
                    confirmPasswordVisible = !_state.value.confirmPasswordVisible
                )
            }


            is AuthEvent.TogglePassword -> {
                _state.value = _state.value.copy(
                    passwordVisible = !_state.value.passwordVisible
                )
            }

            is AuthEvent.SubmitLogin -> {
                login()
            }

            is AuthEvent.SubmitRegistration -> {
                register()
            }

            AuthEvent.ClearError -> {
                _state.value = _state.value.copy(errorMessage = null)
            }

        }
    }

    private fun login() {
        viewModelScope.launch {
            val email = state.value.email.trim()
            val password = state.value.password.trim()

            if (email.isEmpty() || password.isEmpty()) {
                _state.value = _state.value.copy(errorMessage = "Email & Password required")
                return@launch
            }

            _state.value = _state.value.copy(loading = true)

            try {
                auth.signInWithEmailAndPassword(email, password).await()
                _state.value = _state.value.copy(loading = false, success = true)
                _effect.send(AuthEffect.NavigateToDashboard)

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    loading = false,
                    errorMessage = e.localizedMessage ?: "Login Failed"
                )
            }
        }
    }

    private fun register() {
        viewModelScope.launch {
            val email = state.value.email.trim()
            val password = state.value.password.trim()
            val confirm = state.value.confirmPassword.trim()

            if (email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                _state.value = _state.value.copy(errorMessage = "All fields required")
                return@launch
            }

            if (password != confirm) {
                _state.value = _state.value.copy(errorMessage = "Passwords do not match")
                return@launch
            }

            _state.value = _state.value.copy(loading = true)

            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                _state.value = _state.value.copy(loading = false, success = true)
                _effect.send(AuthEffect.NavigateToDashboard)

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    loading = false,
                    errorMessage = e.localizedMessage ?: "Registration Failed"
                )
            }
        }
    }
}
