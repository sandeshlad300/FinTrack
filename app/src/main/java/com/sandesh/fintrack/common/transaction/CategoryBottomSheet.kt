package com.sandesh.fintrack.common.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CategoryBottomSheet(
    categories: List<String>,
    onCategorySelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {

        Box(
            modifier = Modifier
                .width(40.dp)
                .height(4.dp)
                .background(
                    Color.White.copy(alpha = 0.3f),
                    RoundedCornerShape(50)
                )
                .align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Select Category",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(16.dp))

        categories.forEach { category ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF1E1B3A))
                    .clickable { onCategorySelected(category) }
                    .padding(16.dp)
            ) {
                Text(
                    text = category,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.height(12.dp))
        }

        Spacer(Modifier.height(24.dp))
    }
}
