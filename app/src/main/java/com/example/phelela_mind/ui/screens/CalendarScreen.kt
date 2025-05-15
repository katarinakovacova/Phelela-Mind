package com.example.phelela_mind.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material3.DatePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(innerPadding: PaddingValues) {
    val datePickerState = rememberDatePickerState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedDateFormatted by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DatePicker(
            state = datePickerState,
            modifier = Modifier.padding(16.dp)
        )

        val selectedDateMillis = datePickerState.selectedDateMillis

        LaunchedEffect(selectedDateMillis) {
            selectedDateMillis?.let {
                val formatted = SimpleDateFormat("dd/MM/yyyy", Locale("en", "GB"))
                    .format(Date(it))
                selectedDateFormatted = formatted
                showDialog = true
            }
        }

        Text(
            text = "Selected date: ${selectedDateFormatted ?: "No date selected"}",
            fontSize = 18.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(top = 16.dp)
        )
    }

    if (showDialog && selectedDateFormatted != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK")
                }
            },
            title = { Text("Selected Date") },
            text = { Text("To-do list for your day: $selectedDateFormatted") }
        )
    }
}