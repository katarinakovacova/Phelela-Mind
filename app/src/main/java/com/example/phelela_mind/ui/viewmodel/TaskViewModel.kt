package com.example.phelela_mind.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phelela_mind.data.task.local.Task
import com.example.phelela_mind.data.task.repository.TaskRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val tasks: StateFlow<List<Task>> = repository.getAllTasks()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private val _selectedDateMillis = MutableStateFlow<Long?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val tasksForSelectedDate: StateFlow<List<Task>> = _selectedDateMillis
        .filterNotNull()
        .flatMapLatest { millis ->
            val startOfDay = getStartOfDayInMillis(millis)
            val endOfDay = getEndOfDayInMillis(millis)
            repository.getTasksForDate(startOfDay, endOfDay)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun setSelectedDate(millis: Long) {
        _selectedDateMillis.value = millis
    }

    fun addTask(title: String, scheduledForDate: Long? = null) {
        viewModelScope.launch {
            val newTask = Task(
                title = title,
                description = null,
                scheduledForDate = scheduledForDate ?: System.currentTimeMillis()
            )
            repository.insertTask(newTask)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun onTaskCheckedChange(task: Task, isChecked: Boolean) {
        val updatedTask = task.copy(isDone = isChecked)
        updateTask(updatedTask)
    }

    private fun getStartOfDayInMillis(millis: Long): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = millis
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }

    private fun getEndOfDayInMillis(millis: Long): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = millis
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }
        return calendar.timeInMillis
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val tasksForToday: StateFlow<List<Task>> = getTasksForDateFlow(todayStart, todayEnd)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private fun getTasksForDateFlow(start: Long, end: Long): Flow<List<Task>> {
        return repository.getTasksForDate(start, end)
    }

    private val todayStart: Long
        @RequiresApi(Build.VERSION_CODES.O)
        get() = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

    private val todayEnd: Long
        @RequiresApi(Build.VERSION_CODES.O)
        get() = LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}
