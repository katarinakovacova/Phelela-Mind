package com.example.phelela_mind.ui.navigation

sealed class Screens(var route: String) {
    data object Home: Screens("home")
    data object Calendar: Screens("calendar")
    data object Finances: Screens("finances")
    data object Settings: Screens("settings")
    data object Task: Screens("task")
}