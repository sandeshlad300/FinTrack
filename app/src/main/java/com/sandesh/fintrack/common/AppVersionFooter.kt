package com.sandesh.fintrack.common


import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sandesh.fintrack.BuildConfig

@Composable
fun AppVersionFooter(
    modifier: Modifier = Modifier
) {
    Text(
        text = "Version ${BuildConfig.VERSION_NAME}",
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = Color.Gray,
        modifier = modifier
    )
}
