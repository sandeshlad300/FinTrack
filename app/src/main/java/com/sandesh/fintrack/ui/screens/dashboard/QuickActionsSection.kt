package com.sandesh.fintrack.ui.screens.dashboard

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                icon = Icons.Default.Add,
                active = true,
                modifier = Modifier.weight(1f),
                onClick = onAddTransactionClick
            )

            ActionCard(
                title = "View Analytics",
                icon = Icons.Default.Edit,
                active = false,
                modifier = Modifier.weight(1f),
                onClick = { /* navigate analytics */ }
            )
        }

        Spacer(Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            ActionCard(
                title = "AI Assistant",
                icon = Icons.Default.AddCircle,
                modifier = Modifier.weight(1f),
                onClick = {}
            )

            ActionCard(
                title = "Goals",
                icon = Icons.Default.Edit,
                active = false,
                modifier = Modifier.weight(1f),
                onClick = {}
            )
        }
    }
}



@Composable
fun ActionCard(
    title: String,
    icon: ImageVector,
    active: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(90.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (active) Color(0xFF2B2C63)
                else Color(0xFF181834)
            )
            .clickable { onClick() }   // 🔥 important
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.Center) {
            Icon(icon, contentDescription = null, tint = Color.White)
            Spacer(Modifier.height(10.dp))
            Text(title, color = Color.White, fontSize = 14.sp)
        }
    }
}

