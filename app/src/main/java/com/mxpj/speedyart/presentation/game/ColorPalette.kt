package com.mxpj.speedyart.presentation.game

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.mxpj.speedyart.R

@Composable
fun ColorPalette(
    colors: List<Int>,
    gameViewModel: GameViewModel,
    observer: LifecycleOwner
) {
    var selectedColor by remember { mutableStateOf(-1) }
    var colorsAmount = remember { mutableStateMapOf<Int, Int>() }
    //by remember { mutableStateListOf() }
    LaunchedEffect(Unit) {
        gameViewModel.selectedColor.observe(observer){
            selectedColor = it
        }
        gameViewModel.picture.observe(observer){
            for((color, colorCellAmount) in it.colorsAmount) {
                colorsAmount[color] = colorCellAmount
            }
        }
    }
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState(), true)
            .background(color = Color.Gray)
    ) {
        colors.forEach {
            val hasColorCells = colorsAmount.getOrDefault(it,1) != 0
            PaletteColor(color = it, gameViewModel, selectedColor, hasColorCells)
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
    selectedColor: Int,
    hasColorCells: Boolean
) {
    Box(
        modifier = Modifier
            .height(80.dp)
            .aspectRatio(1f)
            .clickable { gameViewModel.selectColor(color) }
            .run {
                if(selectedColor == color) {
                    border(4.dp, Color.White)
                } else {
                    this
                }
            }
            .run {
                background(color = Color(color).copy(alpha = 0.2f))
                if(!hasColorCells){
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