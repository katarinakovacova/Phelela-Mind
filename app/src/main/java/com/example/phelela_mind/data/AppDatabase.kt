package com.example.phelela_mind.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.phelela_mind.data.finances.local.BudgetDao
import com.example.phelela_mind.data.finances.local.BudgetEntity
import com.example.phelela_mind.data.task.local.TaskDao
import com.example.phelela_mind.data.task.local.TaskEntity

@Database(entities = [TaskEntity::class, BudgetEntity::class], version = 7, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun budgetDao(): BudgetDao
}
