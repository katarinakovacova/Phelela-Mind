package com.example.phelela_mind.data.finances.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgets")
data class BudgetEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val initial: Double,
    val spent: Double
)