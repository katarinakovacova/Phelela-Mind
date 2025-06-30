package com.example.phelela_mind.data.task.repository

import com.example.phelela_mind.data.task.local.Task
import com.example.phelela_mind.data.task.local.TaskDao
import com.example.phelela_mind.data.task.mapper.toDomain
import com.example.phelela_mind.data.task.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepository(private val dao: TaskDao) {

    fun getAllTasks(): Flow<List<Task>> =
        dao.getAllTasks().map { list -> list.map { it.toDomain() } }

    fun getTasksForDate(startOfDay: Long, endOfDay: Long): Flow<List<Task>> =
        dao.getTasksForDate(startOfDay, endOfDay).map { list -> list.map { it.toDomain() } }

    suspend fun insertTask(task: Task) {
        dao.insertTask(task.toEntity())
    }

    suspend fun updateTask(task: Task) {
        dao.updateTask(task.toEntity())
    }

    suspend fun updateTaskDone(id: Int, isDone: Boolean) {
        dao.updateTaskDone(id, isDone)
    }

    suspend fun deleteTask(task: Task) {
        dao.deleteTask(task.toEntity())
    }

    suspend fun getTaskById(taskId: Int): Task? {
        return dao.getTaskById(taskId)?.toDomain()
    }
}
