package com.sandesh.fintrack.ui.screens.dashboard

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandesh.fintrack.R

@Composable
fun QuickActionsSection(
    onAddTransactionClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Text(
            text = "Quick Actions",
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )

        Spacer(Modifier.height(14.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            ActionCard(
                title = "Add Transaction",
                iconRes = R.drawable.add_transaction,
                active = true,
                modifier = Modifier.weight(1f),
                onClick = onAddTransactionClick
            )

            ActionCard(
                title = "Analytics",
                iconRes = R.drawable.analysis,
                modifier = Modifier.weight(1f),
                onClick = { }
            )
        }

        Spacer(Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            ActionCard(
                title = "AI Assistant",
                iconRes = R.drawable.ai_assistant,
                modifier = Modifier.weight(1f),
                onClick = { }
            )

            ActionCard(
                title = "Goals",
                iconRes = R.drawable.goals,
                modifier = Modifier.weight(1f),
                onClick = { }
            )
        }
    }
}




@Composable
fun ActionCard(
    title: String,
    @DrawableRes iconRes: Int,
    active: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(100.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (active) Color(0xFF2B2C63)
                else Color(0xFF181834)
            )
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (active) Color(0xFF6366F1)
                        else Color(0xFF2A2A4A)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(Modifier.height(8.dp))

            Text(
                text = title,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
