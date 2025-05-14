package com.example.phelela_mind.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phelela_mind.data.TaskDao
import com.example.phelela_mind.data.TaskEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TaskViewModel(private val taskDao: TaskDao) : ViewModel() {

    val tasks: StateFlow<List<TaskEntity>> = taskDao.getAllTasks()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun addTask(title: String) {
        viewModelScope.launch {
            val newTask = TaskEntity(title = title, description = null)
            taskDao.insertTask(newTask)
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            taskDao.deleteTask(task)
        }
    }

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            taskDao.updateTask(task)
        }
    }

    fun onTaskCheckedChange(task: TaskEntity, isChecked: Boolean) {
        val updatedTask = task.copy(isDone = isChecked)
        updateTask(updatedTask)
    }
}
