package com.sandesh.fintrack.common.transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sandesh.fintrack.ui.theme.TextSecondary

@Composable
fun NoteField(
    note: String,
    onValueChange: (String) -> Unit
) {
    Column {

        Text(
            text = "Note (Optional)",
            color = TextSecondary,
            style = MaterialTheme.typography.labelMedium
        )

        Spacer(Modifier.height(6.dp))

        OutlinedTextField(
            value = note,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 55.dp, max = 150.dp),
            placeholder = {
                Text(
                    text = "Add a description...",
                    color = Color.LightGray
                )
            },
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = Color.White
            ),
            maxLines = 3,
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
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )
    }
}
