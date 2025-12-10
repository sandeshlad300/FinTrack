package com.sandesh.fintrack.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlue,
    onPrimary = TextPrimary,

    secondary = TealAccent,
    onSecondary = Color.Black,

    background = DarkNavy,
    onBackground = TextPrimary,

    surface = NavySecondary,
    onSurface = TextPrimary,

    error = ErrorRed,
    onError = TextPrimary,

    outline = BorderBlue
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = TextPrimary,

    secondary = TealAccent,
    onSecondary = Color.Black,

    background = Color(0xFFF4F7FA),
    onBackground = Color(0xFF0D1526),

    surface = Color.White,
    onSurface = Color(0xFF0D1526),

    error = ErrorRed,
    onError = Color.White,

    outline = PrimaryBlue
)

@Composable
fun FinTrackTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val color = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = color,
        typography = FinTrackTypography,
        shapes = FinTrackShapes,
        content = content
    )
}