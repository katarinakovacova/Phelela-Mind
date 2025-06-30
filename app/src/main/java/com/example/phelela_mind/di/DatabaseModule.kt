package com.example.phelela_mind.di

import androidx.room.Room
import com.example.phelela_mind.data.AppDatabase
import com.example.phelela_mind.data.finances.local.BudgetDao
import com.example.phelela_mind.data.finances.repository.BudgetRepository
import com.example.phelela_mind.data.task.local.TaskDao
import com.example.phelela_mind.data.task.repository.TaskRepository
import com.example.phelela_mind.ui.viewmodel.BudgetViewModel
import com.example.phelela_mind.ui.viewmodel.TaskViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    single<TaskDao> { get<AppDatabase>().taskDao() }
    single<BudgetDao> { get<AppDatabase>().budgetDao() }
}

val repositoryModule = module {
    single { TaskRepository(get()) }
    single { BudgetRepository(get()) }
}

val viewModelModule = module {
    viewModel { TaskViewModel(get()) }
    viewModel { BudgetViewModel(get()) }
}
