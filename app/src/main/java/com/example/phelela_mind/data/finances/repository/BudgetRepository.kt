package com.example.phelela_mind.data.finances.repository

import com.example.phelela_mind.data.finances.local.BudgetDao
import com.example.phelela_mind.data.finances.local.BudgetEntity

class BudgetRepository(private val dao: BudgetDao) {
    suspend fun getAllBudgets(): List<BudgetEntity> = dao.getAll()
    suspend fun insertBudget(budget: BudgetEntity) = dao.insert(budget)
    suspend fun updateBudget(budget: BudgetEntity) = dao.update(budget)
    suspend fun deleteBudget(budget: BudgetEntity) = dao.delete(budget)
}
