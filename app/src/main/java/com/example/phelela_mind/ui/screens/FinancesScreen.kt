package com.example.phelela_mind.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.phelela_mind.data.finances.local.Budget
import com.example.phelela_mind.ui.components.budget.BudgetCard
import com.example.phelela_mind.ui.components.budget.BudgetDialog
import com.example.phelela_mind.ui.components.budget.ExpenseDialog
import com.example.phelela_mind.ui.viewmodel.BudgetViewModel

@Composable
fun FinancesScreen(
    innerPadding: PaddingValues,
    viewModel: BudgetViewModel = org.koin.androidx.compose.koinViewModel()
) {
    val budgets = viewModel.budgets
    var showAddDialog by remember { mutableStateOf(false) }
    var budgetToEdit by remember { mutableStateOf<Budget?>(null) }

    Scaffold(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
                floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add budget")
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            Text(
                "Budgets",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            if (budgets.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No budgets yet")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(budgets, key = { it.id }) { budget ->
                        BudgetCard(
                            budget = budget,
                            onAddExpense = { budgetToEdit = budget },
                            onRemove = { viewModel.removeBudget(budget) }
                        )
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        BudgetDialog(
            title = "New budget",
            confirmLabel = "Create",
            onConfirm = { name, amount ->
                viewModel.addBudget(name, amount)
                showAddDialog = false
            },
            onDismiss = { showAddDialog = false }
        )
    }

    budgetToEdit?.let { target ->
        ExpenseDialog(
            budgetName = target.name,
            onConfirm = { expense ->
                viewModel.addExpense(target, expense)
                budgetToEdit = null
            },
            onDismiss = { budgetToEdit = null }
        )
    }
}
