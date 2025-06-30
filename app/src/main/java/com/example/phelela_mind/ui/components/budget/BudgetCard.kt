package com.example.phelela_mind.ui.components.budget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.phelela_mind.data.finances.local.Budget

@Composable
fun BudgetCard(
    budget: Budget,
    onAddExpense: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(budget.name, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                IconButton(onClick = onRemove) {
                    Icon(Icons.Default.Clear, contentDescription = "Delete budget")
                }
            }

            LinearProgressIndicator(
                progress = { budget.progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
            )

            Text(
                "${budget.remaining} / ${budget.initial}",
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )

            Button(
                onClick = onAddExpense,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp)
            ) {
                Text("Add expense")
            }
        }
    }
}
