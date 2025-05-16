package com.example.phelela_mind.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.phelela_mind.domain.sudoku.GenerateSudokuUseCase
import com.example.phelela_mind.domain.sudoku.MaskSudokuUseCase
import com.example.phelela_mind.ui.components.sudoku.ActionBar
import com.example.phelela_mind.ui.components.sudoku.GameTopBar
import com.example.phelela_mind.ui.components.sudoku.NumberSelector
import com.example.phelela_mind.ui.components.sudoku.Overlay
import com.example.phelela_mind.ui.components.sudoku.SudokuGrid
import kotlinx.coroutines.delay

@Composable
fun SudokuScreen(modifier: Modifier = Modifier) {
    val sudokuSolver = remember { GenerateSudokuUseCase() }
    val maskSudoku = remember { MaskSudokuUseCase() }

    var completeSudoku by remember { mutableStateOf(sudokuSolver.generateSudokuGrid()) }
    val initialMask = remember { maskSudoku.generateVisibleMask() }

    var initialSudokuState by remember {
        mutableStateOf(
            completeSudoku.mapIndexed { row, rowList ->
                rowList.mapIndexed { col, value ->
                    if (initialMask[row][col]) value else null
                }
            }
        )
    }

    var sudokuState by remember { mutableStateOf(initialSudokuState) }
    var originalCells by remember { mutableStateOf(initialMask.map { row -> row.map { it } }) }
    var selectedCell by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    var time by remember { mutableIntStateOf(0) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var isOverlayVisible by remember { mutableStateOf(false) }

    val stopTimer = {
        isTimerRunning = false
        isOverlayVisible = true
    }

    val startTimer = {
        isTimerRunning = true
        isOverlayVisible = false
    }

    LaunchedEffect(Unit) { startTimer() }

    LaunchedEffect(isTimerRunning) {
        if (isTimerRunning) {
            while (true) {
                delay(1000L)
                time += 1
            }
        }
    }

    val minutes = (time / 60).toString().padStart(2, '0')
    val seconds = (time % 60).toString().padStart(2, '0')

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        GameTopBar(
            onNewGame = {
                time = 0
                startTimer()

                completeSudoku = sudokuSolver.generateSudokuGrid()
                val newSudokuMask = maskSudoku.generateVisibleMask()
                val newSudoku = completeSudoku.mapIndexed { row, rowList ->
                    rowList.mapIndexed { col, value ->
                        if (newSudokuMask[row][col]) value else null
                    }
                }

                sudokuState = newSudoku
                initialSudokuState = newSudoku.map { it.toList() }
                originalCells = newSudoku.map { row -> row.map { it != null } }
                selectedCell = null
            },
            minutes = minutes,
            seconds = seconds,
            onStop = stopTimer,
            onStart = startTimer
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isOverlayVisible) {
            Overlay(onDismiss = {
                isOverlayVisible = false
                startTimer()
            })
        }

        SudokuGrid(
            sudoku = sudokuState,
            originalCells = originalCells,
            selectedCell = selectedCell
        ) { row, col ->
            if (!originalCells[row][col]) {
                selectedCell = if (selectedCell == Pair(row, col)) null else Pair(row, col)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ActionBar(
            onErase = {
                selectedCell?.let { (row, col) ->
                    if (!originalCells[row][col]) {
                        sudokuState = sudokuState.mapIndexed { r, rowList ->
                            rowList.mapIndexed { c, cell ->
                                if (r == row && c == col) null else cell
                            }
                        }
                    }
                }
            },
            onHint = {
                selectedCell?.let { (row, col) ->
                    sudokuState = sudokuState.mapIndexed { r, rowList ->
                        rowList.mapIndexed { c, cell ->
                            if (r == row && c == col) completeSudoku[row][col] else cell
                        }
                    }
                }
            },
            onRestart = {
                sudokuState = initialSudokuState.map { it.toList() }
                selectedCell = null
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        NumberSelector { number ->
            selectedCell?.let { (row, col) ->
                if (!originalCells[row][col]) {
                    sudokuState = sudokuState.mapIndexed { r, rowList ->
                        rowList.mapIndexed { c, cell ->
                            if (r == row && c == col) number else cell
                        }
                    }
                }
            }
        }
    }
}
