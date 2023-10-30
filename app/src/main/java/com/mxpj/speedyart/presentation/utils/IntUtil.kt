package com.mxpj.speedyart.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mxpj.speedyart.R

@Composable
fun Int.toStringTime(): String {
    val minutes = this / 60
    val seconds = this % 60
    val timeStringRepresentation = if(minutes == 0) {
        stringResource(R.string.time_seconds, seconds)
    } else {
        stringResource(R.string.time_minutes_and_seconds, minutes, seconds)
    }
    return timeStringRepresentation
}