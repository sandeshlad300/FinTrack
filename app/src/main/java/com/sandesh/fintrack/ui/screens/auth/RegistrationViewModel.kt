package com.sandesh.fintrack.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch



class RegistrationViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state

    private val _effect = Channel<AuthEffect>()
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: AuthEvent) {
        when (event) {

            is AuthEvent.EmailChanged ->
                _state.value = _state.value.copy(email = event.value)

            is AuthEvent.PasswordChanged ->
                _state.value = _state.value.copy(password = event.value)

            is AuthEvent.ConfirmPasswordChanged ->
                _state.value = _state.value.copy(confirmPassword = event.value)

            is AuthEvent.NameChanged ->
                _state.value = _state.value.copy(name = event.value)

            is AuthEvent.TogglePassword ->
                _state.value = _state.value.copy(passwordVisible = !_state.value.passwordVisible)

            is AuthEvent.TogglePasswordVisibility ->
                _state.value = _state.value.copy(
                    createPasswordVisible = !_state.value.createPasswordVisible
                )

            is AuthEvent.ToggleConfirmPasswordVisibility ->
                _state.value = _state.value.copy(
                    confirmPasswordVisible = !_state.value.confirmPasswordVisible
                )

            AuthEvent.ClearAllFields ->
                _state.value = AuthState()

            AuthEvent.SubmitLogin -> login()

            AuthEvent.SubmitRegistration -> register()

            AuthEvent.ForgotPasswordClicked -> resetPassword()

            else -> Unit
        }
    }

    // ---------------- LOGIN ----------------
    private fun login() {
        viewModelScope.launch {

            val email = state.value.email.trim()
            val password = state.value.password.trim()

            if (email.isEmpty() || password.isEmpty()) {
                sendError("Email and password are required")
                return@launch
            }

            _state.value = _state.value.copy(loading = true)

            repository.login(email, password)
                .onSuccess { name ->
                    _state.value = _state.value.copy(
                        loading = false,
                        success = true,
                        name = name
                    )
                    _effect.send(AuthEffect.NavigateToDashboard(name))
                }
                .onFailure {
                    _state.value = _state.value.copy(loading = false)
                    sendError(parseFirebaseError(it))
                }
        }
    }

    // ---------------- REGISTER ----------------
    private fun register() {
        viewModelScope.launch {

            val s = state.value

            if (s.name.isBlank() || s.email.isBlank()
                || s.password.isBlank() || s.confirmPassword.isBlank()
            ) {
                sendError("All fields are required")
                return@launch
            }

            if (s.password != s.confirmPassword) {
                sendError("Passwords do not match")
                return@launch
            }

            _state.value = s.copy(loading = true)

            repository.register(s.name, s.email, s.password)
                .onSuccess {
                    _state.value = _state.value.copy(loading = false, success = true)
                    _effect.send(AuthEffect.SwitchToLogin)
                }
                .onFailure {
                    _state.value = _state.value.copy(loading = false)
                        sendError(parseFirebaseError(it))
                }
        }
    }

    // ---------------- RESET PASSWORD ----------------
    private fun resetPassword() {
        viewModelScope.launch {
            val email = state.value.email.trim()

            if (email.isEmpty()) {
                sendError("Please enter your email first")
                return@launch
            }

            repository.sendPasswordReset(email)
                .onSuccess {
                    _effect.send(AuthEffect.ShowSuccess("Password reset email sent"))
                }
                .onFailure {
                    sendError("Failed to send reset email")
                }
        }
    }

    private suspend fun sendError(message: String) {
        _state.value = _state.value.copy(errorMessage = message)
        _effect.send(AuthEffect.ShowError(message))
    }
}

// -------------------------------------------------------------------------
// Error Parser
// -------------------------------------------------------------------------
private fun parseFirebaseError(t: Throwable): String {
    val msg = t.localizedMessage ?: return "Something went wrong"

    return when {
        msg.contains("password is invalid", true) -> "Incorrect password"
        msg.contains("no user record", true) -> "User not found"
        msg.contains("badly formatted", true) -> "Invalid email format"
        msg.contains("already in use", true) -> "Email already registered"
        msg.contains("at least", true) -> "Password too weak"
        else -> "Authentication failed"
    }
}
