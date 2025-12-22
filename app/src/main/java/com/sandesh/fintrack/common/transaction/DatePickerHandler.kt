package com.sandesh.fintrack.common.transaction

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sandesh.fintrack.ui.theme.PrimaryBlue
import com.sandesh.fintrack.ui.theme.TextSecondary

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerHandler(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Text(
                text = "OK",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        val millis = datePickerState.selectedDateMillis
                        if (millis != null) {
                            val formattedDate = java.time.Instant
                                .ofEpochMilli(millis)
                                .atZone(java.time.ZoneId.systemDefault())
                                .toLocalDate()
                                .toString() // yyyy-MM-dd
                            onDateSelected(formattedDate)
                        }
                        onDismiss()
                    },
                color = PrimaryBlue
            )
        },
        dismissButton = {
            Text(
                text = "Cancel",
                modifier = Modifier.padding(16.dp),
                color = TextSecondary
            )
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
