package com.sandesh.fintrack.common

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times

@Composable
fun AnimatedTabRow(
    tabs: List<String>,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    tabHeight: Dp = 45.dp,
    cornerRadius: Dp = 8.dp,
    backgroundColor: Color = Color.White,
    indicatorColor: Color = Color(0xFF1877F2),
    selectedTextColor: Color = Color.White,
    unSelectedTextColor: Color = Color(0xFF1E88E5)
) {
    val tabWidth = 150.dp
    val containerWidth = tabWidth * tabs.size

    val indicatorOffset by animateDpAsState(
        targetValue = selectedTab * tabWidth,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ),
        label = "TabIndicator"
    )

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        Box(modifier = Modifier.width(containerWidth)) {

            // Background
            Box(
                modifier = Modifier
                    .width(containerWidth)
                    .height(tabHeight)
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(backgroundColor)
            )

            // Indicator
            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .width(tabWidth)
                    .height(tabHeight)
                    .clip(RoundedCornerShape(cornerRadius))
                    .shadow(
                        elevation = 8.dp,
                        shape = RoundedCornerShape(cornerRadius)
                    )
                    .background(indicatorColor)
            )

            // Tabs
            Row(
                modifier = Modifier
                    .width(containerWidth)
                    .height(tabHeight)
            ) {
                tabs.forEachIndexed { index, title ->
                    Box(
                        modifier = Modifier
                            .width(tabWidth)
                            .height(tabHeight)
                            .clickable { onTabSelected(index) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (selectedTab == index)
                                selectedTextColor
                            else
                                unSelectedTextColor
                        )
                    }
                }
            }
        }
    }
}

