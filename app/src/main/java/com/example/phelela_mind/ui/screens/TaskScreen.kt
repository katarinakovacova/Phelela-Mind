package com.example.phelela_mind.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.phelela_mind.data.TaskEntity
import com.example.phelela_mind.ui.components.task.TaskItem
import com.example.phelela_mind.ui.viewmodel.TaskViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: TaskViewModel = koinViewModel()

    var taskText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    val tasks by viewModel.tasks.collectAsState()
    var taskBeingEdited by remember { mutableStateOf<TaskEntity?>(null) }
    var editedText by remember { mutableStateOf(TextFieldValue("")) }

    var selectedDateMillis by rememberSaveable { mutableStateOf<Long?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }

    val dateFormat = remember { SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()) }
    val datePickerState = rememberDatePickerState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = taskText,
            onValueChange = { taskText = it },
            label = { Text("New Task") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { showDatePicker = true }) {
            Text(
                text = selectedDateMillis?.let { "Task scheduled for: ${dateFormat.format(Date(it))}" }
                    ?: "Select date"
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (taskText.text.isNotBlank()) {
                    viewModel.addTask(taskText.text, selectedDateMillis)
                    taskText = TextFieldValue("")
                    selectedDateMillis = null
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onEdit = {
                            taskBeingEdited = task
                            editedText = TextFieldValue(task.title)
                        },
                        onDelete = {
                            viewModel.deleteTask(task)
                        },
                        onCheckedChange = { isChecked ->
                            viewModel.onTaskCheckedChange(task, isChecked)
                        }
                    )
                }
            }
        }
    }

    if (taskBeingEdited != null) {
        AlertDialog(
            onDismissRequest = { taskBeingEdited = null },
            confirmButton = {
                TextButton(onClick = {
                    val updatedTask = taskBeingEdited!!.copy(title = editedText.text)
                    viewModel.updateTask(updatedTask)
                    taskBeingEdited = null
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { taskBeingEdited = null }) {
                    Text("Cancel")
                }
            },
            title = { Text("Edit Task") },
            text = {
                OutlinedTextField(
                    value = editedText,
                    onValueChange = { editedText = it },
                    label = { Text("Task Title") },
                    singleLine = true
                )
            }
        )
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    selectedDateMillis = datePickerState.selectedDateMillis
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
