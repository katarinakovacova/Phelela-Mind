package com.example.phelela_mind.ui.screens

import SudokuViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.phelela_mind.ui.components.sudoku.LevelSelectionDialog
import com.example.phelela_mind.ui.components.sudoku.LowerActionBar
import com.example.phelela_mind.ui.components.sudoku.NumberSelector
import com.example.phelela_mind.ui.components.sudoku.Overlay
import com.example.phelela_mind.ui.components.sudoku.SudokuGrid
import com.example.phelela_mind.ui.components.sudoku.UpperActionBar

@Composable
fun SudokuScreen(
    viewModel: SudokuViewModel,
    modifier: Modifier = Modifier
) {
    val completeGrid by viewModel.completeGrid.collectAsState()
    val sudokuState by viewModel.sudokuState.collectAsState()
    val originalCells by viewModel.originalCells.collectAsState()
    val selectedCell by viewModel.selectedCell.collectAsState()
    val difficulty by viewModel.difficulty.collectAsState()
    val time by viewModel.time.collectAsState()

    var isOverlayVisible by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }
    var showLevelDialog by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }

    val stopTimer = {
        viewModel.stopTimer()
        isOverlayVisible = true
    }

    val startTimer = {
        viewModel.startTimer()
        isOverlayVisible = false
    }

    val minutes = (time / 60).toString().padStart(2, '0')
    val seconds = (time % 60).toString().padStart(2, '0')

    if (showLevelDialog) {
        LevelSelectionDialog(
            onDismissRequest = { showLevelDialog = false },
            onLevelSelected = { selectedDifficulty ->
                showLevelDialog = false
                viewModel.generateNewGame(selectedDifficulty)
                startTimer()
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        UpperActionBar(
            onNewGame = { showLevelDialog = true },
            minutes = minutes,
            seconds = seconds,
            onStop = stopTimer,
            onStart = startTimer,
            difficulty = difficulty
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isOverlayVisible) {
            Overlay(onDismiss = {
                isOverlayVisible = false
                startTimer()
            })
        }

        if (sudokuState.isNotEmpty() && originalCells.isNotEmpty()) {
            SudokuGrid(
                sudoku = sudokuState,
                originalCells = originalCells,
                selectedCell = selectedCell
            ) { row, col ->
                if (!originalCells[row][col]) {
                    viewModel.selectCell(row, col)
                }
            }
        } else {
            Spacer(modifier = Modifier.height(200.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        LowerActionBar(
            onErase = {
                selectedCell?.let { (row, col) ->
                    viewModel.eraseNumber(row, col)
                }
            },
            onHint = {
                selectedCell?.let { (row, col) ->
                    viewModel.getHint(row, col)
                }
            },
            onRestart = {
                viewModel.restartGame()
                startTimer()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        NumberSelector { number ->
            selectedCell?.let { (row, col) ->
                viewModel.setNumber(row, col, number)
            }
        }
    }
}
