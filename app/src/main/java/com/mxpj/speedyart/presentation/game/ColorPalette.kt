package com.mxpj.speedyart.presentation.game

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorPalette(
    colors: List<Int>,
    onColorClickListener: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState(), true)
            .background(color = Color.Gray)
    ) {
        colors.forEach {
            PaletteColor(color = it, onColorClickListener = onColorClickListener)
            Spacer(
                modifier = Modifier
                    .height(80.dp)
                    .width(5.dp)
            )
        }
    }
}

@Composable
private fun PaletteColor(
    color: Int,
    onColorClickListener: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .height(80.dp)
            .aspectRatio(1f)
            .background(color = Color(color))
            .clickable { onColorClickListener.invoke(color) }
    ) {

    }
}