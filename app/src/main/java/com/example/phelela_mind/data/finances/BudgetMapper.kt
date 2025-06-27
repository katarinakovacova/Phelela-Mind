package com.example.phelela_mind.data.finances

import androidx.compose.runtime.mutableDoubleStateOf

fun BudgetEntity.toBudget(): Budget {
    return Budget(id, name, initial, mutableDoubleStateOf(spent))
}

fun Budget.toEntity(): BudgetEntity {
    return BudgetEntity(id, name, initial, spent.value)
}