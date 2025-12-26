package com.sandesh.fintrack.ui.screens.auth

data class AuthState(
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val passwordVisible: Boolean = false,
    val loading: Boolean = false,
    val createPasswordVisible: Boolean = false,
    val confirmPasswordVisible: Boolean = false,
    val errorMessage: String? = null,
    val success: Boolean = false
)


sealed class AuthEvent {
    data class EmailChanged(val value: String) : AuthEvent()
    data class NameChanged(val value: String) : AuthEvent()
    data class PasswordChanged(val value: String) : AuthEvent()
    data class ConfirmPasswordChanged(val value: String) : AuthEvent()
    object TogglePasswordVisibility : AuthEvent()
    object ToggleCreatePasswordVisibility : AuthEvent()
    object ToggleConfirmPasswordVisibility : AuthEvent()
    object TogglePassword : AuthEvent()
    object SubmitLogin : AuthEvent()
    object SubmitRegistration : AuthEvent()
    object ClearError : AuthEvent()
    object ClearAllFields : AuthEvent()
    data object ForgotPasswordClicked : AuthEvent()
}


sealed class AuthEffect {
    data class NavigateToDashboard(val name: String) : AuthEffect()
    object SwitchToLogin : AuthEffect()
    data class ShowError(val message: String) : AuthEffect()
    data class ShowSuccess(val message: String) : AuthEffect()
}
