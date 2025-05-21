package com.example.phelela_mind.data.sudoku

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface SudokuDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSudoku(sudoku: SudokuEntity): Long

    @Update
    suspend fun updateSudoku(sudoku: SudokuEntity)

    @Delete
    suspend fun deleteSudoku(sudoku: SudokuEntity)

    @Query("SELECT * FROM sudoku WHERE id = :id")
    suspend fun getSudokuById(id: Long): SudokuEntity?

    @Query("SELECT * FROM sudoku ORDER BY createdAt DESC")
    suspend fun getAllSudoku(): List<SudokuEntity>

    @Query("SELECT * FROM sudoku WHERE isCompleted = 0 ORDER BY createdAt DESC LIMIT 1")
    suspend fun getUnfinishedSudoku(): SudokuEntity?

    @Query("SELECT * FROM sudoku WHERE isCompleted = 1 ORDER BY completedAt DESC")
    suspend fun getCompletedSudoku(): List<SudokuEntity>

    @Query("DELETE FROM sudoku")
    suspend fun deleteAll()
}
