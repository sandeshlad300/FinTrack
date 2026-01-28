package com.sandesh.fintrack.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandesh.fintrack.R
import com.sandesh.fintrack.common.AnimatedTabRow
import com.sandesh.fintrack.ui.theme.DarkNavy
import kotlinx.coroutines.flow.Flow


@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel,
    onNavigateToDashboard: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var selectedTab by remember { mutableStateOf(0) }

    HandleAuthEffects(
        effectFlow = viewModel.effect,
        snackbarHostState = snackbarHostState,
        onNavigateToDashboard = onNavigateToDashboard,
        onSwitchToLogin = { selectedTab = 0 }
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = DarkNavy
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            ScrollableAuthContent(
                state = state,
                selectedTab = selectedTab,
                onTabChange = {
                    selectedTab = it
                    viewModel.onEvent(AuthEvent.ClearAllFields)
                },
                viewModel = viewModel,
                modifier = Modifier.weight(1f)
            )

            SocialLoginFooter()
        }
    }
}


@Composable
private fun HandleAuthEffects(
    effectFlow: Flow<AuthEffect>,
    snackbarHostState: SnackbarHostState,
    onNavigateToDashboard: (String) -> Unit,
    onSwitchToLogin: () -> Unit
) {
    LaunchedEffect(Unit) {
        effectFlow.collect { effect ->
            when (effect) {
                is AuthEffect.NavigateToDashboard ->
                    onNavigateToDashboard(effect.name)

                AuthEffect.SwitchToLogin ->
                    onSwitchToLogin()

                is AuthEffect.ShowSuccess ->
                    snackbarHostState.showSnackbar(effect.message)

                is AuthEffect.ShowError ->
                    snackbarHostState.showSnackbar(effect.message)
            }
        }
    }
}


@Composable
private fun ScrollableAuthContent(
    modifier: Modifier = Modifier,
    state: AuthState,
    selectedTab: Int,
    onTabChange: (Int) -> Unit,
    viewModel: RegistrationViewModel
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(24.dp))

        AuthHeader(selectedTab)

        Spacer(Modifier.height(30.dp))

        AnimatedTabRow(
            tabs = listOf("Log In", "Register"),
            selectedTab = selectedTab,
            onTabSelected = onTabChange
        )

        Spacer(Modifier.height(24.dp))

        AuthContent(
            selectedTab = selectedTab,
            state = state,
            viewModel = viewModel
        )

        Spacer(Modifier.height(24.dp))
    }
}


@Composable
private fun AuthHeader(selectedTab: Int) {
    Text(
        text = if (selectedTab == 0) "Welcome Back" else "Create Account",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )

    Text(
        text = if (selectedTab == 0)
            "Take control of your finances."
        else
            "Register to get started.",
        fontSize = 14.sp,
        color = Color.Gray
    )
}


@Composable
private fun AuthContent(
    selectedTab: Int,
    state: AuthState,
    viewModel: RegistrationViewModel
) {
    if (selectedTab == 0) {
        LoginContent(
            email = state.email,
            password = state.password,
            passwordVisible = state.passwordVisible,
            biometricEnabled = false,
            loading = state.loading,
            onEmailChange = { viewModel.onEvent(AuthEvent.EmailChanged(it)) },
            onPasswordChange = { viewModel.onEvent(AuthEvent.PasswordChanged(it)) },
            onTogglePassword = { viewModel.onEvent(AuthEvent.TogglePassword) },
            onLoginClick = { viewModel.onEvent(AuthEvent.SubmitLogin) },
            onForgotPasswordClick = {
                viewModel.onEvent(AuthEvent.ForgotPasswordClicked)
            }
        )
    } else {
        RegistrationContent(
            name = state.name,
            onNameChange = { viewModel.onEvent(AuthEvent.NameChanged(it)) },
            email = state.email,
            password = state.password,
            confirmPassword = state.confirmPassword,
            createPasswordVisible = state.createPasswordVisible,
            confirmPasswordVisible = state.confirmPasswordVisible,
            onEmailChange = { viewModel.onEvent(AuthEvent.EmailChanged(it)) },
            onPasswordChange = { viewModel.onEvent(AuthEvent.PasswordChanged(it)) },
            onConfirmPasswordChange = {
                viewModel.onEvent(AuthEvent.ConfirmPasswordChanged(it))
            },
            onTogglePassword = {
                viewModel.onEvent(AuthEvent.TogglePasswordVisibility)
            },
            onToggleConfirmPassword = {
                viewModel.onEvent(AuthEvent.ToggleConfirmPasswordVisibility)
            },
            onRegisterClick = {
                viewModel.onEvent(AuthEvent.SubmitRegistration)
            },
            loading = state.loading
        )
    }
}


@Composable
private fun SocialLoginFooter() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.google),
                contentDescription = "Google Login",
                modifier = Modifier.size(40.dp).clickable { }
            )

            Spacer(Modifier.width(12.dp))

            Image(
                painter = painterResource(R.drawable.facebook),
                contentDescription = "Facebook Login",
                modifier = Modifier.size(40.dp).clickable { }
            )
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text = "By creating an account, you agree to our Terms of Service and Privacy Policy",
            fontSize = 10.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}
