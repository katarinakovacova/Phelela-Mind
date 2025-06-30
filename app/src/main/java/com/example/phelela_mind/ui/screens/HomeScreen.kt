package com.example.phelela_mind.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.phelela_mind.ui.components.task.TaskItem
import com.example.phelela_mind.ui.viewmodel.TaskViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.*
import com.example.phelela_mind.data.task.local.Task

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    viewModel: TaskViewModel = koinViewModel()
) {
    val tasks by viewModel.tasksForToday.collectAsState()

    val todayFormatted = remember {
        val date = Date()
        SimpleDateFormat("dd MMMM yyyy", Locale("en", "GB")).format(date)
    }

    var taskToDelete by remember { mutableStateOf<Task?>(null) }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "To-Do List – $todayFormatted",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (tasks.isEmpty()) {
            Text(text = "You have no assignments today.", fontSize = 16.sp)
        } else {
            LazyColumn {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onDelete = {
                            taskToDelete = task
                        },
                        onCheckedChange = { isChecked ->
                            viewModel.onTaskCheckedChange(task, isChecked)
                        }
                    )
                }
            }
        }
    }

    if (taskToDelete != null) {
        AlertDialog(
            onDismissRequest = { taskToDelete = null },
            title = { Text("Confirm deletion") },
            text = { Text("Are you sure you want to delete this task?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteTask(taskToDelete!!)
                    taskToDelete = null
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { taskToDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}
