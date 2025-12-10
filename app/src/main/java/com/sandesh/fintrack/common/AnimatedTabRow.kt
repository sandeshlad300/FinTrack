package com.sandesh.fintrack.common

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times

@Composable
fun AnimatedTabRow(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf("Log In", "Register")

    val tabWidth = 110.dp
    val containerWidth = tabWidth * tabs.size

    val indicatorOffset by animateDpAsState(
        targetValue = selectedTab * tabWidth,
        animationSpec = tween(300, easing = FastOutSlowInEasing)
    )

    // OUTER CONTAINER (center whole row)
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        // INNER BOX (left-based layout)
        Box(
            modifier = Modifier.width(containerWidth)
        ) {

            // BACKGROUND
            Box(
                modifier = Modifier
                    .width(containerWidth)
                    .height(45.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFffffff))
            )

            // SLIDING INDICATOR (NOW ALWAYS LEFT-ALIGNED)
            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .width(tabWidth)
                    .height(45.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shadow(
                        elevation = 8.dp,                   // shadow strength
                        shape = RoundedCornerShape(8.dp),  // same shape as indicator
                        ambientColor = Color(0x33000000),   // faint grey (20% opacity)
                        spotColor = Color(0x33000000)       // faint grey
                    )
                    .background(Color(0xFF1877F2))
            )

            // TABS
            Row(
                modifier = Modifier
                    .width(containerWidth)
                    .height(45.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                tabs.forEachIndexed { index, text ->
                    Box(
                        modifier = Modifier
                            .width(tabWidth)
                            .height(45.dp)
                            .clickable { onTabSelected(index) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = text,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (selectedTab == index) Color.White else Color(0xFF1E88E5)
                        )
                    }
                }
            }
        }
    }
}
