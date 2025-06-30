package com.example.phelela_mind.data.task.local

data class Task(
    val id: Int = 0,
    val title: String,
    val description: String? = null,
    val isDone: Boolean = false,
    val scheduledForDate: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)
