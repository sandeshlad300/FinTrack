package com.sandesh.fintrack.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandesh.fintrack.R
import com.sandesh.fintrack.common.FTButton

@Composable
fun RegistrationContent(
    email: String,
    password: String,
    confirmPassword: String,

    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,

    passwordVisible: Boolean,
    confirmPasswordVisible: Boolean,
    onTogglePassword: () -> Unit,
    onToggleConfirmPassword: () -> Unit,

    onRegisterClick: () -> Unit
) {

    // ------------------------ EMAIL ------------------------
    Text("Email", color = Color.White, fontSize = 14.sp)
    Spacer(Modifier.height(8.dp))

    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        placeholder = { Text("Enter your email", color = Color.LightGray) },
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        singleLine = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email Icon",
                tint = Color.Gray
            )
        },
        colors = textFieldColors(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )
    )

    Spacer(Modifier.height(24.dp))


    // ------------------------ PASSWORD ------------------------
    Text("Password", color = Color.White, fontSize = 14.sp)
    Spacer(Modifier.height(8.dp))

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        placeholder = { Text("Create password", color = Color.LightGray) },
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = onTogglePassword) {
                Icon(
                    painter = painterResource(
                        id = if (passwordVisible) R.drawable.eye else R.drawable.hidden
                    ),
                    contentDescription = "Toggle Password Visibility",
                    tint = Color.Unspecified
                )
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None
        else PasswordVisualTransformation(),
        colors = textFieldColors()
    )

    Spacer(Modifier.height(24.dp))


    // ------------------------ CONFIRM PASSWORD ------------------------
    Text("Confirm Password", color = Color.White, fontSize = 14.sp)
    Spacer(Modifier.height(8.dp))

    OutlinedTextField(
        value = confirmPassword,
        onValueChange = onConfirmPasswordChange,
        placeholder = { Text("Confirm password", color = Color.LightGray) },
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = onToggleConfirmPassword) {
                Icon(
                    painter = painterResource(
                        id = if (confirmPasswordVisible) R.drawable.eye else R.drawable.hidden
                    ),
                    contentDescription = "Toggle Confirm Password Visibility",
                    tint = Color.Unspecified
                )
            }
        },
        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None
        else PasswordVisualTransformation(),
        colors = textFieldColors()
    )

    Spacer(Modifier.height(28.dp))


    // ------------------------ REGISTER BUTTON ------------------------

    FTButton(
        text = "Register",
        onClick = {
            onRegisterClick
        }
    )
}

// ------------------------ Reusable Colors ------------------------

@Composable
private fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    unfocusedBorderColor = Color(0xFF2E3A55),
    focusedBorderColor = Color(0xFF5DA8FF),
    unfocusedContainerColor = Color(0xFF152238),
    focusedContainerColor = Color(0xFF152238),
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
)
