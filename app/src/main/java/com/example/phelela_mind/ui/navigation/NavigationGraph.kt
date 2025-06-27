package com.example.phelela_mind.ui.navigation


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.phelela_mind.ui.screens.CalendarScreen
import com.example.phelela_mind.ui.screens.FinancesScreen
import com.example.phelela_mind.ui.screens.HomeScreen
import com.example.phelela_mind.ui.screens.NotificationScreen
import com.example.phelela_mind.ui.screens.SettingsScreen
import com.example.phelela_mind.ui.screens.StatisticScreen
import com.example.phelela_mind.ui.screens.TaskScreen
import com.example.phelela_mind.ui.viewmodel.TaskViewModel
import org.koin.androidx.compose.koinViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetUpNavigationGraph(
    navController: NavHostController,
    innerPadding: PaddingValues
) {

    NavHost(navController = navController, startDestination = Screens.Home.route) {
        composable(Screens.Home.route) {
            val viewModel: TaskViewModel = koinViewModel()
            HomeScreen(innerPadding = innerPadding, viewModel = viewModel)
        }

        composable(Screens.Task.route) {
            TaskScreen(modifier = Modifier.padding(innerPadding))
        }

        composable(Screens.Calendar.route) {
            val viewModel: TaskViewModel = koinViewModel()
            CalendarScreen(innerPadding = innerPadding, viewModel = viewModel)
        }

        composable(Screens.Notification.route) {
            NotificationScreen(innerPadding = innerPadding)
        }

        composable("finances") { FinancesScreen(innerPadding = innerPadding) }


        composable(Screens.Settings.route) {
            SettingsScreen(innerPadding = innerPadding)
        }

        composable(Screens.Statistics.route) {
            StatisticScreen(innerPadding = innerPadding)
        }
    }
}
