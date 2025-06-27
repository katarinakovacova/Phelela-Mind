package com.example.phelela_mind.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.phelela_mind.data.task.TaskDao
import com.example.phelela_mind.data.task.TaskEntity

@Database(entities = [TaskEntity::class], version = 6, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
