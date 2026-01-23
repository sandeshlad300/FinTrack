package com.sandesh.fintrack.ui.screens.splash

import com.sandesh.fintrack.R
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandesh.fintrack.common.AppVersionFooter

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    onNavigate: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
        viewModel.onEvent(SplashEvent.Start)
        viewModel.effect.collect {
            when (it) {
                SplashEffect.NavigateToIntro -> onNavigate()
            }
        }
    }

    val gradient = Brush.verticalGradient(
        listOf(
            Color(0xFF7B2FFF),
            Color(0xFF2D6CDF),
            Color(0xFF05C7A5)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + slideInVertically(initialOffsetY = { 40 }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { -40 }),
            modifier = Modifier.align(Alignment.Center)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .background(
                            color = Color(0x44FFFFFF),
                            shape = RoundedCornerShape(22.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.app_icon),
                        contentDescription = "FinTrack Logo",
                        modifier = Modifier.size(80.dp)
                    )
                }

                Spacer(Modifier.height(20.dp))

                Text(
                    text = "FinTrack",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Track. Save. Grow.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }

        // Footer is now ALWAYS visible
        AppVersionFooter(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp) // visual spacing only
        )
    }
}
