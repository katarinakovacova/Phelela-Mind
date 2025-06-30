package com.example.phelela_mind.data.task.mapper

import com.example.phelela_mind.data.task.local.Task
import com.example.phelela_mind.data.task.local.TaskEntity

fun TaskEntity.toDomain() = Task(
    id = id,
    title = title,
    description = description,
    isDone = isDone,
    scheduledForDate = scheduledForDate,
    createdAt = createdAt
)

fun Task.toEntity() = TaskEntity(
    id = id,
    title = title,
    description = description,
    isDone = isDone,
    scheduledForDate = scheduledForDate,
    createdAt = createdAt
)
