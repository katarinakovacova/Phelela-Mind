package com.example.phelela_mind.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String?,
    val isDone: Boolean = false,
    val scheduledForDate: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)
