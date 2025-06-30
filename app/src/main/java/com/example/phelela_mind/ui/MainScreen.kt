package com.example.phelela_mind.ui

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.phelela_mind.ui.navigation.NavigationBarBody
import com.example.phelela_mind.ui.navigation.NavigationBarHeader
import com.example.phelela_mind.ui.navigation.Screens
import com.example.phelela_mind.ui.navigation.SetUpNavigationGraph
import com.example.phelela_mind.ui.viewmodel.TaskViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: TaskViewModel = koinViewModel(),
    darkThemeEnabled: Boolean,
    onToggleDarkTheme: (Boolean) -> Unit,
) {
    val tasksForToday by viewModel.tasksForToday.collectAsState()

    val homeBadgeCount = tasksForToday.size

    val allTasks by viewModel.tasks.collectAsState()

    val unfinishedTasksCount = allTasks.count { !it.isDone }

    val items = listOf(
        com.example.phelela_mind.ui.navigation.NavigationItem(
            title = "Home",
            route = Screens.Home.route,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            badgeCount = homeBadgeCount
        ),
        com.example.phelela_mind.ui.navigation.NavigationItem(
            title = "Task",
            route = Screens.Task.route,
            selectedIcon = Icons.Filled.Done,
            unselectedIcon = Icons.Outlined.Done,
            badgeCount = unfinishedTasksCount
        ),
        com.example.phelela_mind.ui.navigation.NavigationItem(
            title = "Calendar",
            route = Screens.Calendar.route,
            selectedIcon = Icons.Filled.DateRange,
            unselectedIcon = Icons.Outlined.DateRange,
        ),
        com.example.phelela_mind.ui.navigation.NavigationItem(
            title = "Finances",
            route = Screens.Finances.route,
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
        ),
        com.example.phelela_mind.ui.navigation.NavigationItem(
            title = "Settings",
            route = Screens.Settings.route,
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
        ),
    )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val topBarTitle =
        if (currentRoute != null){
            items[items.indexOfFirst {
                it.route == currentRoute
            }].title
        }else{
            items[0].title
        }
    ModalNavigationDrawer(
        gesturesEnabled = drawerState.isOpen,drawerContent = {
            ModalDrawerSheet(

            ) {
                NavigationBarHeader()
                Spacer(modifier = Modifier.height(8.dp))
                NavigationBarBody(items = items, currentRoute =currentRoute) { currentNavigationItem ->
                    if(currentNavigationItem.route == "share"){
                        Toast.makeText(context,"Share Clicked",Toast.LENGTH_LONG).show()
                    }else{
                        navController.navigate(currentNavigationItem.route){

                            navController.graph.startDestinationRoute?.let { startDestinationRoute ->
                                popUpTo(startDestinationRoute) {
                                    saveState = true
                                }
                            }

                            launchSingleTop = true
                            restoreState = true
                        }
                    }

                    scope.launch {
                        drawerState.close()
                    }
                }
            }
        }, drawerState = drawerState) {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(text = topBarTitle)
                }, navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "menu"
                        )
                    }
                })
            }
        ) {innerPadding->
            SetUpNavigationGraph(
                navController = navController,
                innerPadding = innerPadding,
                darkThemeEnabled = darkThemeEnabled,
                onToggleDarkTheme = onToggleDarkTheme)
        }
    }
}
