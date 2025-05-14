package com.example.phelela_mind.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.DatePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(innerPadding: PaddingValues) {
    val datePickerState = rememberDatePickerState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Select a date",
            fontSize = 30.sp,
            color = Color.Black,
            modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
        )

        DatePicker(
            state = datePickerState,
            modifier = Modifier.padding(16.dp)
        )

        val selectedDateMillis = datePickerState.selectedDateMillis
        val formattedDate = remember(selectedDateMillis) {
            selectedDateMillis?.let {
                SimpleDateFormat("dd/MM/yyyy", Locale("en", "GB")).format(Date(it))
            } ?: "No date selected"
        }

        Text(
            text = "Selected date: $formattedDate",
            fontSize = 18.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
