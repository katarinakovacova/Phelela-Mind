package com.example.phelela_mind.ui.components.sudoku

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ActionBar(
    onErase: () -> Unit,
    onHint: () -> Unit,
    onGetNewSudoku: () -> Unit,
    onRestart: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        ActionButton(text = "Erase", onClick = onErase)
        Spacer(modifier = Modifier.width(8.dp))

        ActionButton(text = "Hint", onClick = onHint)
        Spacer(modifier = Modifier.width(8.dp))

        ActionButton(text = "Restart", onClick = onRestart)
        Spacer(modifier = Modifier.width(8.dp))

        ActionButton(text = "New", onClick = onGetNewSudoku)
    }
}
