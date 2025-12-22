package com.sandesh.fintrack.common.transaction

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandesh.fintrack.ui.screens.transaction.addTransaction.formatAmount
import com.sandesh.fintrack.ui.theme.TextSecondary
import kotlin.math.roundToInt

@Composable
fun AmountInput(
    rawAmount: String,
    isInvalid: Boolean,
    onAmountChange: (String) -> Unit
) {
    val formattedAmount = formatAmount(rawAmount)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "ENTER AMOUNT",
            color = TextSecondary
        )

        Spacer(Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.shake(isInvalid)
        ) {

            Text(
                text = "₹",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.width(6.dp))

            BasicTextField(
                value = rawAmount,
                onValueChange = { input ->
                    if (Regex("^\\d*(\\.\\d{0,2})?$").matches(input)) {
                        onAmountChange(input)
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                cursorBrush = SolidColor(Color.White),
                decorationBox = {
                    if (rawAmount.isEmpty()) {
                        Text(
                            text = "0.00",
                            color = Color.White.copy(alpha = 0.4f),
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text(
                            text = formattedAmount,
                            color = Color.White,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        }
    }
}



fun Modifier.shake(
    trigger: Boolean
): Modifier = composed {

    val offsetX = remember { Animatable(0f) }

    LaunchedEffect(trigger) {
        if (trigger) {
            offsetX.snapTo(0f)
            offsetX.animateTo(-10f, tween(50))
            offsetX.animateTo(10f, tween(50))
            offsetX.animateTo(-6f, tween(50))
            offsetX.animateTo(6f, tween(50))
            offsetX.animateTo(0f, tween(50))
        }
    }

    this.offset { IntOffset(offsetX.value.roundToInt(), 0) }
}

