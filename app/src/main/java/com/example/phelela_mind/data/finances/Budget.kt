package com.example.phelela_mind.data.finances

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import kotlin.math.max

data class Budget(
    val id: Int,
    val name: String,
    val initial: Double,
    var spent: MutableState<Double> = mutableDoubleStateOf(0.0)
) {
    val remaining get() = max(initial - spent.value, 0.0)
    val progress get() = if (initial == 0.0) 0f else (remaining / initial).toFloat()
}
