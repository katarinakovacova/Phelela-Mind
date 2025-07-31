# Phelela-Mind

**Phelela-Mind** is a minimalist Android app that helps you stay organized and focused. It combines a fast to-do list, a calendar view, and simple budgeting tools — all in one place. Whether you're managing daily tasks, tracking events, or monitoring your personal finances, Phelela-Mind helps you stay on track with ease.

---

## Features

- **Navigation Drawer** for easy access to all sections of the app
<p align="center">
  <img src="app/src/main/assets/screens/navigation.png" alt="Navigation Drawer" width="30%" />
  <img src="app/src/main/assets/screens/navigation-dark.png" alt="Navigation Drawer Dark" width="30%" />
</p>

- **Home Screen**: Daily overview of all tasks scheduled for the current day
<p align="center">
  <img src="app/src/main/assets/screens/home-screen.png" alt="Home Screen" width="30%" />
  <img src="app/src/main/assets/screens/home-screen-dark.png" alt="Home Screen" width="30%" />
</p>

- **Task Management**:
    - Create, edit, and delete tasks
    - Assign tasks to specific dates
<p align="center">
  <img src="app/src/main/assets/screens/task-list.png" alt="Task List" width="30%" />
  <img src="app/src/main/assets/screens/task-list-dark.png" alt="Task List Dark" width="30%" />
</p>

- **Calendar View**:
    - Visual calendar interface
    - Tap any date to view tasks for that day
<p align="center">
  <img src="app/src/main/assets/screens/calendar.png" alt="Calendar" width="20%" />
  <img src="app/src/main/assets/screens/calendar-dark.png" alt="Calendar Dark" width="20%" />
  <img src="app/src/main/assets/screens/calendar-list.png" alt="Calendar List" width="20%" />
  <img src="app/src/main/assets/screens/calendar-list-dark.png" alt="Calendar List Dark" width="20%" />
</p>

- **Budget Tracker**:
    - Create custom budgets for various needs
    - Add expenses and monitor remaining budget in real time
<p align="center">
  <img src="app/src/main/assets/screens/budget-with-expense.png" alt="Budget Screen" width="20%" />
  <img src="app/src/main/assets/screens/budgets-dark.png" alt="Settings Screen" width="20%" />
  <img src="app/src/main/assets/screens/new-budget.png" alt="New Budget" width="20%" />
  <img src="app/src/main/assets/screens/expense.png" alt="Expense" width="20%" />
</p>

- **Settings**:
    - Toggle between light and dark mode
<p align="center">
  <img src="app/src/main/assets/screens/settings.png" alt="Settings" width="30%" />
  <img src="app/src/main/assets/screens/settings-dark.png" alt="Settings Dark" width="30%" />
</p>

---

## Built With

Phelela-Mind uses modern Android development tools and libraries, including:

- **Kotlin 2.0**
- **Jetpack Compose**
- **Room** for local database storage
- **Koin** for dependency injection
- **Material 3 UI Components**
- **Android Navigation Component**
- **Dark Mode Support**

---

## Architecture

This project follows the principles of **Clean Architecture** and the **MVVM (Model-View-ViewModel)** pattern, with a clear separation of concerns across the following layers:

```
com.example.phelela_mind
├── data                        # Data layer: Room DB, mappers, repositories
│   ├── finances                # Budget & expense data
│   │   ├── local               # Room entities and DAO for budgeting
│   │   ├── mapper              # Converts between entity and domain
│   │   └── repository          # BudgetRepository implementation
│   ├── task                    # Task data logic
│   │   ├── local               # Room entities and DAO for tasks
│   │   ├── mapper              # Converts between entity and domain
│   │   └── repository          # TaskRepository implementation
│   └── AppDatabase.kt          # Central Room database holder
├── di                          # Dependency injection setup (Koin modules)
│   └── DatabaseModule.kt
├── ui                          # UI layer built with Jetpack Compose
│   ├── components              # Reusable UI widgets
│   │   ├── budget              # Components for budget screen (dialogs, cards)
│   │   └── task                # Task list item composable
│   ├── navigation              # App navigation structure (NavGraph, items)
│   ├── screens                 # Individual screens (Calendar, Tasks, etc.)
│   ├── theme                   # Colors, typography, theme definitions
├── viewmodel                   # ViewModels for tasks and budgeting
│   ├── BudgetViewModel.kt
│   └── TaskViewModel.kt
├── MainScreen.kt               # Main layout with scaffold and navigation drawer
├─ MainActivity.kt             # Entry point for the app
└─ PhelelaMindApp.kt           # Application class (initializes Koin etc.)
```

### Directory Structure

```
com.example.phelela_mind
├── data
│   ├── finances
│   │   ├── local
│   │   │   ├── BudgetEntity.kt
│   │   │   └── BudgetDao.kt
│   │   │   └── Budget.kt
│   │   ├── mapper
│   │   │   └── BudgetMapper.kt
│   │   ├── repository
│   │   │   └── BudgetRepository.kt
│   ├── task
│   │   ├── local
│   │   │   ├── TaskEntity.kt
│   │   │   └── TaskDao.kt
│   │   │   └── Task.kt
│   │   ├── mapper
│   │   │   └── TaskMapper.kt
│   │   ├── repository
│   │   │   └── TaskRepository.kt
│   └── AppDatabase.kt
├── di
│   └── DatabaseModule.kt
├── ui
│   ├── components
│   │   ├── budget
│   │   │   ├── BudgetCard.kt
│   │   │   ├── BudgetDialog.kt
│   │   │   └── ExpenseDialog.kt
│   │   └── task
│   │       └── TaskItem.kt
│   ├── navigation
│   │   ├── NavigationBarLayout.kt
│   │   ├── NavigationGraph.kt
│   │   ├── NavigationItem.kt
│   │   └── Screens.kt
│   ├── screens
│   │   ├── CalendarScreen.kt
│   │   ├── FinancesScreen.kt
│   │   ├── HomeScreen.kt
│   │   ├── SettingsScreen.kt
│   │   └── TaskScreen.kt
│   ├── theme
│   │   ├── Color.kt
│   │   ├── Theme.kt
│   │   └── Type.kt
├── viewmodel
│   ├── BudgetViewModel.kt
│   └── TaskViewModel.kt
├── MainScreen.kt
├─MainActivity.kt
└─PhelelaMindApp.kt
```

## License

This project is licensed under the MIT License.  
See the [LICENSE](./LICENSE) file for details.
