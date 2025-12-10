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

            AuthEvent.ToggleCreatePasswordVisibility -> {
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

            AuthEvent.ClearAllFields -> {
                _state.value = _state.value.copy(
                    email = "",
                    password = "",
                    confirmPassword = "",
                    passwordVisible = false,
                    createPasswordVisible = false,
                    confirmPasswordVisible = false,
                    errorMessage = null
                )
            }

            AuthEvent.ForgotPasswordClicked -> sendForgotPassword()
        }
    }

    private fun updateState(reducer: (AuthState) -> AuthState) {
        _state.value = reducer(_state.value)
    }

    private suspend fun sendError(message: String) {
        _state.value = _state.value.copy(errorMessage = message)
        _effect.send(AuthEffect.ShowError(message))
    }


    private fun login() {
        viewModelScope.launch {
            val email = state.value.email.trim()
            val password = state.value.password.trim()

            if (email.isEmpty() || password.isEmpty()) {
                sendError("Email and Password are required")
                return@launch
            }

            updateState { it.copy(loading = true, errorMessage = null) }

            try {
                auth.signInWithEmailAndPassword(email, password).await()
                updateState { it.copy(loading = false, success = true) }

                _effect.send(AuthEffect.ShowSuccess("Login Successful"))
                _effect.send(AuthEffect.NavigateToDashboard)

            } catch (e: Exception) {
                updateState { it.copy(loading = false) }
                sendError(parseFirebaseError(e))
            }
        }
    }


    private fun register() {
        viewModelScope.launch {
            val email = state.value.email.trim()
            val password = state.value.password.trim()
            val confirm = state.value.confirmPassword.trim()

            if (email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                sendError("All fields are required")
                return@launch
            }

            if (password != confirm) {
                sendError("Passwords do not match")
                return@launch
            }

            updateState { it.copy(loading = true, errorMessage = null) }

            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                updateState { it.copy(loading = false, success = true) }

                _effect.send(AuthEffect.ShowSuccess("Account created successfully"))
                _effect.send(AuthEffect.NavigateToDashboard)

            } catch (e: Exception) {
                updateState { it.copy(loading = false) }
                sendError(parseFirebaseError(e))
            }
        }
    }

    private fun sendForgotPassword() {
        viewModelScope.launch {
            val email = state.value.email.trim()

            if (email.isEmpty()) {
                _effect.send(AuthEffect.ShowError("Please enter your email first"))
                return@launch
            }

            try {
                auth.sendPasswordResetEmail(email).await()
                _effect.send(AuthEffect.ShowSuccess("Password reset email sent"))
            } catch (e: Exception) {
                _effect.send(
                    AuthEffect.ShowError(
                        e.localizedMessage ?: "Failed to send reset email"
                    )
                )
            }
        }
    }




}


private fun parseFirebaseError(e: Exception): String {
    val msg = e.localizedMessage ?: return "Something went wrong"

    return when {
        msg.contains("password is invalid", true) -> "Incorrect password"
        msg.contains("no user record", true) -> "User not found"
        msg.contains("email address is badly formatted", true) -> "Invalid email"
        msg.contains("The email address is already in use", true) -> "Email already registered"
        msg.contains("Password should be at least", true) -> "Password too weak"
        else -> "Authentication failed"
    }
}

