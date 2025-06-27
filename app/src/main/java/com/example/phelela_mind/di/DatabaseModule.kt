package com.example.phelela_mind.di

import org.koin.dsl.module
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import com.example.phelela_mind.data.AppDatabase
import com.example.phelela_mind.data.finances.BudgetDao
import com.example.phelela_mind.data.finances.BudgetRepository
import com.example.phelela_mind.ui.viewmodel.BudgetViewModel
import com.example.phelela_mind.ui.viewmodel.TaskViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

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

    single { get<AppDatabase>().taskDao() }
    single<BudgetDao> { get<AppDatabase>().budgetDao() }

    viewModel { TaskViewModel(get()) }

}

val repositoryModule = module {
    single { BudgetRepository(get()) }
}

val viewModelModule = module {
    viewModel { BudgetViewModel(get()) }}