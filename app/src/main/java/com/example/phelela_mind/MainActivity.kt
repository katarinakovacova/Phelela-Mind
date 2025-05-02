package com.example.phelela_mind

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.phelela_mind.ui.navigation.NavigationBarBody
import com.example.phelela_mind.ui.navigation.NavigationBarHeader
import com.example.phelela_mind.ui.navigation.NavigationItem
import com.example.phelela_mind.ui.navigation.Screens
import com.example.phelela_mind.ui.navigation.SetUpNavigationGraph
import com.example.phelela_mind.ui.theme.NavigationDrawerJetpackComposeTheme

import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationDrawerJetpackComposeTheme {
                val items = listOf(
                    NavigationItem(
                        title = "Home",
                        route = Screens.Home.route,
                        selectedIcon = Icons.Filled.Home,
                        unselectedIcon = Icons.Outlined.Home,
                    ),
                    NavigationItem(
                        title = "Task",
                        route = Screens.Task.route,
                        selectedIcon = Icons.Filled.Done,
                        unselectedIcon = Icons.Outlined.Done,
                    ),
                    NavigationItem(
                        title = "Calendar",
                        route = Screens.Calendar.route,
                        selectedIcon = Icons.Filled.DateRange,
                        unselectedIcon = Icons.Outlined.DateRange,
                    ),
                    NavigationItem(
                        title = "Statistics",
                        route = Screens.Statistics.route,
                        selectedIcon = Icons.Filled.Create,
                        unselectedIcon = Icons.Outlined.Create,
                    ),
                    NavigationItem(
                        title = "Notification",
                        route = Screens.Notification.route,
                        selectedIcon = Icons.Filled.Notifications,
                        unselectedIcon = Icons.Outlined.Notifications,
                        badgeCount = 0
                    ),
                    NavigationItem(
                        title = "Sudoku",
                        route = Screens.Sudoku.route,
                        selectedIcon = Icons.Filled.Favorite,
                        unselectedIcon = Icons.Outlined.Favorite,
                    ),
                    NavigationItem(
                        title = "Profile",
                        route = Screens.Profile.route,
                        selectedIcon = Icons.Filled.Person,
                        unselectedIcon = Icons.Outlined.Person,
                    ),
                    NavigationItem(
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
                        SetUpNavigationGraph(navController = navController, innerPadding = innerPadding)
                    }
                }
            }
        }
    }
}
