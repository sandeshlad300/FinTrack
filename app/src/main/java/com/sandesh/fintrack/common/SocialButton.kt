package com.sandesh.fintrack.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color        // correct Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.sandesh.fintrack.ui.theme.ButtonColors
import com.sandesh.fintrack.ui.theme.TextPrimary


@Composable
fun FTButton(
    @DrawableRes icon: Int? = null,
    text: String,
    modifier: Modifier = Modifier,
    background: Color = ButtonColors,   // Your UI card field background
    textColor: Color = TextPrimary,     // White text
    loading: Boolean = false,           // Loader flag
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(12.dp),
        color = background,
        shadowElevation = 0.dp,
        onClick = {
            if (!loading) onClick()  // prevent clicks when loading
        }
    ) {

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            if (loading) {
                androidx.compose.material3.CircularProgressIndicator(
                    color = textColor,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                // Only show icon if provided
                icon?.let {
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        colorFilter = ColorFilter.tint(textColor) // icon colored same as text
                    )
                    Spacer(Modifier.width(12.dp))
                }

                Text(
                    text = text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor
                )
            }
        }
    }
}


