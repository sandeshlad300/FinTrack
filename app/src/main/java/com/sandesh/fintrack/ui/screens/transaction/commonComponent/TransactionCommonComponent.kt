package com.sandesh.fintrack.ui.screens.transaction.commonComponent

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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandesh.fintrack.R
import com.sandesh.fintrack.ui.screens.transaction.transaction.TransactionFilter
import com.sandesh.fintrack.ui.screens.transaction.transaction.TransactionItem

@Composable
fun FiltersRow(selected: TransactionFilter, onSelect: (TransactionFilter) -> Unit) {

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
fun TransactionCard(item: TransactionItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF1E293B))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(item.iconBg)
            ) {}


            Spacer(modifier = Modifier.width(12.dp))


            Column(modifier = Modifier.weight(1f)) {
                Text(item.title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text("${item.category} • ${item.time}", color = Color.Gray, fontSize = 12.sp)
            }


            Text(
                item.amount,
                color = if (item.isExpense) Color(0xFFFF6B6B) else Color(0xFF2ECC71),
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun AddButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        FloatingActionButton(onClick = onClick) {
            Icon(Icons.Default.Add, contentDescription = null)
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
