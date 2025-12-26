package com.sandesh.fintrack.common.transaction

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandesh.fintrack.R
import com.sandesh.fintrack.ui.screens.transaction.addTransaction.formatAmount
import com.sandesh.fintrack.ui.screens.transaction.transaction.TransactionFilter
import com.sandesh.fintrack.ui.screens.transaction.transaction.TransactionItem
import com.sandesh.fintrack.ui.screens.transaction.transactionSuccess.TransactionUiModel

@Composable
fun FiltersRow(
    selected: TransactionFilter,
    onSelect: (TransactionFilter) -> Unit)
{

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        item {
            CommonFilterChip(
                label = "All",
                isSelected = selected == TransactionFilter.ALL,
                onClick = { onSelect(TransactionFilter.ALL) }
            )
        }

        item {
            CommonFilterChip(
                label = "Income",
                isSelected = selected == TransactionFilter.INCOME,
                onClick = { onSelect(TransactionFilter.INCOME) }
            )
        }

        item {
            CommonFilterChip(
                label = "Expense",
                isSelected = selected == TransactionFilter.EXPENSE,
                onClick = { onSelect(TransactionFilter.EXPENSE) }
            )
        }

        item {
            CommonFilterChip(
                label = "Date",
                isSelected = selected == TransactionFilter.DATE,
                onClick = { onSelect(TransactionFilter.DATE) }
            )
        }
    }
}






@Composable
fun SectionTitle(text: String) {
    Text(text, color = Color.Gray, modifier = Modifier.padding(vertical = 12.dp))
}


@Composable
fun TransactionCard(item: TransactionUiModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF1E293B))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            // 🔹 LEFT ICON
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        if (item.source == "Income")
                            Color(0xFF1E3A2F)
                        else
                            Color(0xFF3A1E1E)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.transaction),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(
                        if (item.source == "Income")
                            Color(0xFF2ECC71)
                        else
                            Color(0xFFFF6B6B)
                    )
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // 🔹 TITLE + DATE
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.category,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = formatDateTime(item.date),
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

            }

            // 🔹 AMOUNT (RIGHT SIDE)
            Text(
                text = formatAmount( "₹"+item.amount.toString()),
                color = if (item.source == "Income")
                    Color(0xFF2ECC71)
                else
                    Color(0xFFFF6B6B),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}




@Composable
fun CommonFilterChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    val background by animateColorAsState(
        if (isSelected) Color(0xFF6366F1) else Color(0xFF0F172A),
        animationSpec = tween(250)
    )

    val textColor by animateColorAsState(
        if (isSelected) Color.White else Color.Gray,
        animationSpec = tween(250)
    )

    val borderColor by animateColorAsState(
        if (isSelected) Color(0xFF818CF8) else Color(0xFF334155),
        animationSpec = tween(250)
    )

    val scale by animateFloatAsState(
        if (isSelected) 1.03f else 1f
    )

    Box(
        modifier = Modifier
            .scale(scale)
            .width(120.dp)
            .height(42.dp)
            .clip(RoundedCornerShape(50))
            .background(background)
            .border(1.dp, borderColor, RoundedCornerShape(50))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                text = label,
                color = textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
