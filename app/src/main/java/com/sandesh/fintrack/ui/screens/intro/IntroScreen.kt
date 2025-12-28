package com.sandesh.fintrack.ui.screens.intro

import androidx.annotation.RawRes
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.sandesh.fintrack.ui.theme.DarkNavy
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.collections.lastIndex


@Composable
fun IntroScreen(
    pages: List<IntroPage>,
    viewModel: IntroViewModel,
    onNavigateToLogin: () -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pages.size }
    )

    val scope = rememberCoroutineScope()

    // ---------- SMOOTH AUTO-SWIPE LOOP ----------
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000) // wait before sliding
            val nextPage = (pagerState.currentPage + 1).coerceAtMost(pages.lastIndex)
            if (pagerState.currentPage < pages.lastIndex) {
                pagerState.animateScrollToPage(
                    page = nextPage,
                    animationSpec = tween(
                        durationMillis = 600,
                        easing = LinearOutSlowInEasing
                    )
                )
                viewModel.onEvent(IntroEvent.PageChanged(nextPage))
            } else {
                break
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                IntroEffect.NavigateToLogin -> onNavigateToLogin()
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkNavy)
    ) {
        // ---------- FULLSCREEN PAGER ----------
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val p = pages[page]

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(p.gradientColors.map { Color(it) })),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LottieHeroRaw(p.lottieRes, Modifier.size(360.dp))

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = p.title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = p.subtitle,
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // ---------- INDICATORS + BUTTONS OVERLAY ----------
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IntroPagerIndicator(pagerState, pages.size)

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Skip",
                    modifier = Modifier.weight(1f).clickable {
                        viewModel.onEvent(IntroEvent.Skip)
                    },
                    color = Color.Gray
                )

                OutlinedButton(
                    onClick = {
                        scope.launch {
                            if (pagerState.currentPage < pages.lastIndex) {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                viewModel.onEvent(
                                    IntroEvent.PageChanged(pagerState.currentPage + 1)
                                )
                            } else {
                                viewModel.onEvent(IntroEvent.Finish)
                            }
                        }
                    },
                    modifier = Modifier.width(160.dp),
                    shape = RoundedCornerShape(30.dp),
                    border = BorderStroke(1.5.dp, Color(0xFF34E0C4)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(0xFF34E0C4)
                    )
                ) {
                    Text(
                        text = if (pagerState.currentPage == pages.lastIndex) "Get Started" else "Next",
                        fontWeight = FontWeight.SemiBold
                    )
                }

            }
        }
    }
}



@Composable
fun IntroPagerIndicator(
    pagerState: PagerState,
    pageCount: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            val isSelected = pagerState.currentPage == index

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(if (isSelected) 10.dp else 8.dp)
                    .background(
                        color = if (isSelected) Color(0xFF34E0C4) else Color.LightGray,
                        shape = CircleShape
                    )
            )
        }
    }
}


@Composable
fun LottieHeroRaw(@RawRes resId: Int, modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(resId)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier
    )
}