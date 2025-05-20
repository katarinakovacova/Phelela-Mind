package com.example.phelela_mind.di

import com.example.phelela_mind.domain.sudoku.GenerateSudokuUseCase
import com.example.phelela_mind.domain.sudoku.MaskSudokuUseCase
import com.example.phelela_mind.ui.viewmodel.SudokuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val sudokuModule = module {
    single { GenerateSudokuUseCase() }
    single { MaskSudokuUseCase() }

    viewModel {
        SudokuViewModel(
            generateSudokuUseCase = get(),
            maskSudokuUseCase = get()
        )
    }
}
