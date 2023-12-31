package com.mxpj.speedyart.presentation.game

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mxpj.speedyart.R

@Composable
fun ColorPalette(
    colors: List<Int>,
    gameViewModel: GameViewModel
) {
    val gameColorsData by gameViewModel.gameColorsData.observeAsState()
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState(), true)
    ) {
        colors.forEach {
            val hasColorCells = gameColorsData!!.coloredCellsAmount.getOrDefault(it,1) != 0
            PaletteColor(color = it, gameViewModel, gameColorsData!!.selectedColor, hasColorCells)
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
    gameViewModel: GameViewModel,
    selectedColor: Int?,
    hasColorCells: Boolean
) {
    Box(
        modifier = Modifier
            .height(80.dp)
            .aspectRatio(1f)
            .clickable { gameViewModel.selectColor(color) }
            .clip(RoundedCornerShape(100))
            .run {
                if (selectedColor == color) {
                    border(4.dp, Color.White, shape = RoundedCornerShape(100))
                } else {
                    this
                }
            }
            .run {
                background(color = Color(color).copy(alpha = 0.2f))
                if (!hasColorCells) {
                    background(color = Color(color).copy(alpha = 0.2f))
                } else {
                    background(color = Color(color))
                }
            }
    ) {
        if(!hasColorCells){
            Image(
                painter = painterResource(R.drawable.ic_checkmark),
                contentDescription = "Выполнено",
                modifier = Modifier
                    .height(25.dp)
                    .width(25.dp)
                    .align(Alignment.Center)
            )
        }
    }
}