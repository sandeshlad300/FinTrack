package com.sandesh.fintrack.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
fun LoginContent(
    email: String,
    password: String,
    passwordVisible: Boolean,
    biometricEnabled: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePassword: () -> Unit,
    onLoginClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Email", color = Color.White, fontSize = 14.sp)
    }
    Spacer(Modifier.height(8.dp))

    OutlinedTextField(
        value = email,
        onValueChange = { onEmailChange(it) },
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
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color(0xFF2E3A55),
            focusedBorderColor = Color(0xFF5DA8FF),
            unfocusedContainerColor = Color(0xFF152238),
            focusedContainerColor = Color(0xFF152238),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )
    )



    Spacer(Modifier.height(24.dp))

    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Password", color = Color.White, fontSize = 14.sp)
    }
    Spacer(Modifier.height(8.dp))

    OutlinedTextField(
        value = password,
        onValueChange = { onPasswordChange(it) },
        placeholder = { Text("Enter your password", color = Color.LightGray) },
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        singleLine = true,
        trailingIcon = {
            IconButton(onClick = onTogglePassword) {
                Icon(
                    painter = painterResource(
                        id = if (passwordVisible)
                            R.drawable.eye
                        else
                            R.drawable.hidden
                    ),
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    tint = Color.Unspecified // Use PNG colors as-is
                )
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None
        else PasswordVisualTransformation(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color(0xFF2E3A55),
            focusedBorderColor = Color(0xFF5DA8FF),
            unfocusedContainerColor = Color(0xFF152238),
            focusedContainerColor = Color(0xFF152238),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        )
    )

    Spacer(Modifier.height(14.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Text("Forgot Password?",
            color = Color(0xFF5DA8FF),
            modifier = Modifier.clickable { onForgotPasswordClick() }
        )
    }

    Spacer(Modifier.height(32.dp))

    // Login Button
    FTButton(
        text = "Log In",
        onClick = {
            onLoginClick()
        }
    )
    Spacer(Modifier.height(20.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.fingerprint), // your PNG icon
            contentDescription = "Biometric Icon",
            modifier = Modifier
                .size(30.dp)
                .padding(end = 8.dp)
        )

        Text("Use Biometric Login", color = Color.White)
    }
}
