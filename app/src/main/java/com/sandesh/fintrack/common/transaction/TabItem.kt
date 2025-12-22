package com.sandesh.fintrack.common.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sandesh.fintrack.ui.theme.PrimaryBlue
import com.sandesh.fintrack.ui.theme.TealAccent
import com.sandesh.fintrack.ui.theme.TextSecondary

@Composable
fun TabItem(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (selected) PrimaryBlue else TealAccent
            )
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Text(
            text = title,
            color = if (selected) Color.White else TextSecondary,
            style = MaterialTheme.typography.labelLarge
        )
    }
}
