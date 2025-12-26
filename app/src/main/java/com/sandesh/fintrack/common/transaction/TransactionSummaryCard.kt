package com.sandesh.fintrack.common.transaction

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandesh.fintrack.ui.screens.transaction.transactionSuccess.TransactionUiModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@Composable
fun TransactionSummaryCard(
    transaction: TransactionUiModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF241A33),
                        Color(0xFF1A1227)
                    )
                )
            )
            .padding(vertical = 20.dp)
    ) {
        Column {

            // ---- TOTAL AMOUNT ----
            Text(
                text = "TOTAL AMOUNT",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color(0xFFB9B2C6),
                fontSize = 11.sp,
                letterSpacing = 1.2.sp
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "₹ ${transaction.amount}",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.White,
                fontSize = 34.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(20.dp))

            Divider(color = Color.White.copy(alpha = 0.08f))

            // ---- ROWS ----
            SummaryRowExact(
                iconBg = Color(0xFF6F3CC3),
                icon = Icons.Rounded.ShoppingCart,
                label = "Category",
                value = transaction.category
            )

            Divider(color = Color.White.copy(alpha = 0.05f))

            SummaryRowExact(
                iconBg = Color(0xFF22C3A6),
                icon = Icons.Rounded.DateRange,
                label = "Date",
                value =  formatDateTime(transaction.date)
            )

            Divider(color = Color.White.copy(alpha = 0.05f))

            SummaryRowExact(
                iconBg = Color(0xFF7E7E8A),
                icon = Icons.Rounded.AccountCircle,
                label = "Source",
                value = transaction.source
            )
        }
    }
}


@Composable
private fun SummaryRowExact(
    iconBg: Color,
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }

        Spacer(Modifier.width(14.dp))

        Text(
            text = label,
            modifier = Modifier.weight(1f),
            color = Color(0xFFB9B2C6),
            fontSize = 13.sp
        )

        Text(
            text = value,
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}


fun formatDateTime(millis: Long): String {
    val formatter = SimpleDateFormat(
        "dd MMM yyyy, hh:mm a",
        Locale.getDefault()
    )
    return formatter.format(Date(millis))
}


@RequiresApi(Build.VERSION_CODES.O)
fun parseDateWithCurrentTimeToMillis(date: String): Long {
    // DatePicker gives: yyyy-MM-dd
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val localDate = LocalDate.parse(date, inputFormatter)

    val currentTime = LocalTime.now()

    return LocalDateTime.of(localDate, currentTime)
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

