package com.example.phelela_mind.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.phelela_mind.data.sudoku.SudokuDao
import com.example.phelela_mind.data.sudoku.SudokuEntity
import com.example.phelela_mind.data.task.TaskDao
import com.example.phelela_mind.data.task.TaskEntity

@Database(entities = [TaskEntity::class, SudokuEntity::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun sudokuDao(): SudokuDao
}

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `sudoku` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `initialGrid` TEXT NOT NULL,
                `maskedGrid` TEXT NOT NULL,
                `userGrid` TEXT NOT NULL,
                `difficulty` TEXT NOT NULL,
                `timeSpent` INTEGER NOT NULL,
                `isCompleted` INTEGER NOT NULL,
                `completedAt` INTEGER,
                `createdAt` INTEGER NOT NULL
            )
            """.trimIndent()
        )
    }
}
