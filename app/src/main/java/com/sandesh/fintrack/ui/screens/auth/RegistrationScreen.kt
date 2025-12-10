package com.sandesh.fintrack.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
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


@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel,
    onNavigateToDashboard: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    val scrollState = rememberScrollState()

    var selectedTab by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            if (effect is AuthEffect.NavigateToDashboard) {
                onNavigateToDashboard()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkNavy)
            .verticalScroll(scrollState)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(25.dp))

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

        Spacer(Modifier.height(30.dp))

        AnimatedTabRow(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        Spacer(Modifier.height(25.dp))

        if (selectedTab == 0) {
            LoginContent(
                email = state.email,
                password = state.password,
                passwordVisible = state.passwordVisible,
                biometricEnabled = false,
                onEmailChange = { viewModel.onEvent(AuthEvent.EmailChanged(it)) },
                onPasswordChange = { viewModel.onEvent(AuthEvent.PasswordChanged(it)) },
                onTogglePassword = { viewModel.onEvent(AuthEvent.TogglePassword) },
                onToggleBiometric = {},
                onLoginClick = { viewModel.onEvent(AuthEvent.SubmitLogin) }
            )
        } else {
            RegistrationContent(
                email = state.email,
                password = state.password,
                confirmPassword = state.confirmPassword,
                passwordVisible = state.passwordVisible,
                confirmPasswordVisible = state.confirmPasswordVisible,

                onEmailChange = { viewModel.onEvent(AuthEvent.EmailChanged(it)) },
                onPasswordChange = { viewModel.onEvent(AuthEvent.PasswordChanged(it)) },
                onConfirmPasswordChange = { viewModel.onEvent(AuthEvent.ConfirmPasswordChanged(it)) },

                onTogglePassword = { viewModel.onEvent(AuthEvent.TogglePasswordVisibility) },
                onToggleConfirmPassword = { viewModel.onEvent(AuthEvent.ToggleConfirmPasswordVisibility) },

                onRegisterClick = { viewModel.onEvent(AuthEvent.SubmitRegistration) }
            )


        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription = "Google Login",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {}
            )

            Spacer(Modifier.width(8.dp))

            Image(
                painter = painterResource(id = R.drawable.facebook),
                contentDescription = "Facebook Login",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {}
            )
        }

        Spacer(Modifier.height(20.dp))

        Text(
            text = "By creating an account, you agree to our Terms of Service and Privacy Policy",
            fontSize = 10.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
    }

    if (state.errorMessage != null) {
        // TODO: Show Dialog
    }
}


