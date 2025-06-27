package com.example.phelela_mind.ui.components.budget

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun ExpenseDialog(
    budgetName: String,
    onConfirm: (expense: Double) -> Unit,
    onDismiss: () -> Unit
) {
    var expense by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = { onConfirm(expense.toDoubleOrNull() ?: 0.0) },
                enabled = expense.toDoubleOrNull() != null
            ) { Text("Add") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } },
        title = { Text("Add expense to $budgetName") },
        text = {
            OutlinedTextField(
                value = expense,
                onValueChange = { expense = it },
                label = { Text("Expense amount") },
                singleLine = true
            )
        }
    )
}
