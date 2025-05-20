package com.example.phelela_mind.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.phelela_mind.domain.sudoku.GenerateSudokuUseCase
import com.example.phelela_mind.domain.sudoku.MaskSudokuUseCase
import com.example.phelela_mind.domain.sudoku.SudokuDifficulty
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SudokuViewModel(
    private val generateSudokuUseCase: GenerateSudokuUseCase,
    private val maskSudokuUseCase: MaskSudokuUseCase
) : ViewModel() {

    private val _completeGrid = MutableStateFlow<Array<IntArray>>(emptyArray())
    val completeGrid: StateFlow<Array<IntArray>> = _completeGrid

    private val _visibleMask = MutableStateFlow<Array<Array<Boolean>>>(emptyArray())
    val visibleMask: StateFlow<Array<Array<Boolean>>> = _visibleMask

    private val _sudokuState = MutableStateFlow<List<List<Int?>>>(emptyList())
    val sudokuState: StateFlow<List<List<Int?>>> = _sudokuState

    private val _originalCells = MutableStateFlow<List<List<Boolean>>>(emptyList())
    val originalCells: StateFlow<List<List<Boolean>>> = _originalCells

    private val _selectedCell = MutableStateFlow<Pair<Int, Int>?>(null)
    val selectedCell: StateFlow<Pair<Int, Int>?> = _selectedCell

    private val _difficulty = MutableStateFlow(SudokuDifficulty.MEDIUM)
    val difficulty: StateFlow<SudokuDifficulty> = _difficulty

    init {
        generateNewGame(_difficulty.value)
    }

    fun generateNewGame(level: SudokuDifficulty) {
        viewModelScope.launch {
            _difficulty.value = level

            val fullGrid = generateSudokuUseCase.generateSudokuGrid()
            val mask = maskSudokuUseCase.generateVisibleMask(level)

            _completeGrid.value = fullGrid
            _visibleMask.value = mask

            _sudokuState.value = fullGrid.mapIndexed { row, rowList ->
                rowList.mapIndexed { col, value ->
                    if (mask[row][col]) value else null
                }
            }

            _originalCells.value = mask.map { row -> row.toList() }
            _selectedCell.value = null
        }
    }

    fun selectCell(row: Int, col: Int) {
        _selectedCell.value = if (_selectedCell.value == Pair(row, col)) null else Pair(row, col)
    }

    fun setNumber(row: Int, col: Int, number: Int) {
        if (_originalCells.value[row][col]) return

        val currentState = _sudokuState.value.toMutableList()
        val rowList = currentState[row].toMutableList()
        rowList[col] = number
        currentState[row] = rowList
        _sudokuState.value = currentState
    }

    fun eraseNumber(row: Int, col: Int) {
        if (_originalCells.value[row][col]) return

        val currentState = _sudokuState.value.toMutableList()
        val rowList = currentState[row].toMutableList()
        rowList[col] = null
        currentState[row] = rowList
        _sudokuState.value = currentState
    }

    fun getHint(row: Int, col: Int) {
        if (_originalCells.value[row][col]) return

        val answer = _completeGrid.value[row][col]
        setNumber(row, col, answer)
    }

    fun restartGame() {
        _sudokuState.value = _completeGrid.value.mapIndexed { row, rowList ->
            rowList.mapIndexed { col, value ->
                if (_visibleMask.value[row][col]) value else null
            }
        }
        _selectedCell.value = null
    }

    fun changeDifficulty(level: SudokuDifficulty) {
        generateNewGame(level)
    }
}
