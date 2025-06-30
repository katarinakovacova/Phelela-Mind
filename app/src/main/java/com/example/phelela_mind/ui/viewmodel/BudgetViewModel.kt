package com.example.phelela_mind.ui.viewmodel

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phelela_mind.data.finances.local.Budget
import com.example.phelela_mind.data.finances.local.BudgetEntity
import com.example.phelela_mind.data.finances.repository.BudgetRepository
import com.example.phelela_mind.data.finances.mapper.toBudget
import com.example.phelela_mind.data.finances.mapper.toEntity
import kotlinx.coroutines.launch

class BudgetViewModel(
    private val repository: BudgetRepository
) : ViewModel() {

    var budgets = mutableStateListOf<Budget>()
        private set

    init { refreshFromDb() }

    private fun refreshFromDb() = viewModelScope.launch {
        budgets.clear()
        budgets.addAll(repository.getAllBudgets().map { it.toBudget() })
    }

    fun addBudget(name: String, initial: Double) = viewModelScope.launch {
        val id = repository.insertBudget(
            BudgetEntity(name = name, initial = initial, spent = 0.0)
        ).toInt()

        budgets.add(
            Budget(id = id, name = name, initial = initial, spent = mutableDoubleStateOf(0.0))
        )
    }

    fun addExpense(budget: Budget, expense: Double) {
        budget.spent.value += expense
        viewModelScope.launch {
            repository.updateBudget(budget.toEntity())
        }
    }

    fun removeBudget(budget: Budget) {
        budgets.remove(budget)
        viewModelScope.launch {
            repository.deleteBudget(budget.toEntity())
        }
    }
}
