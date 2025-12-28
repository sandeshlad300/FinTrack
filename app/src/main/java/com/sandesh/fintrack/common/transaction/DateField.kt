package com.sandesh.fintrack.common.transaction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sandesh.fintrack.ui.theme.TextSecondary

@Composable
fun DateField(
    date: String,
    placeholder: String = "Select date",
    onClick: () -> Unit
) {
    Column {

        Text(
            text = "Date",
            color = TextSecondary,
            style = MaterialTheme.typography.labelMedium
        )

        Spacer(Modifier.height(6.dp))

        OutlinedTextField(
            value = date,
            onValueChange = {}, // no manual input
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .clickable { onClick() },
            readOnly = true,
            enabled = false, // prevents keyboard
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.LightGray
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Date Picker",
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
                disabledTextColor = Color.White,
                disabledContainerColor = Color(0xFF152238),
                disabledBorderColor = Color(0xFF2E3A55),
            )
        )
    }
}


