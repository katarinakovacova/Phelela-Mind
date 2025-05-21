package com.example.phelela_mind.di

import org.koin.dsl.module
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import com.example.phelela_mind.data.AppDatabase
import com.example.phelela_mind.data.MIGRATION_4_5
import com.example.phelela_mind.ui.viewmodel.TaskViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database"
        )
            .addMigrations(MIGRATION_4_5)
            .build()
    }

    single { get<AppDatabase>().taskDao() }
    single { get<AppDatabase>().sudokuDao() }

    viewModel { TaskViewModel(get()) }
}
