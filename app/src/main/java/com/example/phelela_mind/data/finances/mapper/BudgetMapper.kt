package com.example.phelela_mind.data.finances.mapper

import androidx.compose.runtime.mutableDoubleStateOf
import com.example.phelela_mind.data.finances.local.Budget
import com.example.phelela_mind.data.finances.local.BudgetEntity

fun BudgetEntity.toBudget(): Budget {
    return Budget(id, name, initial, mutableDoubleStateOf(spent))
}

fun Budget.toEntity(): BudgetEntity {
    return BudgetEntity(id, name, initial, spent.value)
}