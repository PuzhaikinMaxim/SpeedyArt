package com.mxpj.speedyart.presentation.game

import android.app.Activity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner

@Composable
fun ColorPalette(
    colors: List<Int>,
    gameViewModel: GameViewModel,
    observer: LifecycleOwner
) {
    var selectedColor by remember { mutableStateOf(-1) }
    LaunchedEffect(Unit) {
        gameViewModel.selectedColor.observe(observer){
            selectedColor = it
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
            PaletteColor(color = it, gameViewModel, selectedColor)
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
    selectedColor: Int
) {
    Box(
        modifier = Modifier
            .height(80.dp)
            .aspectRatio(1f)
            .background(color = Color(color))
            .clickable { gameViewModel.selectColor(color) }
            .run {
                if(selectedColor == color) {
                    border(4.dp, Color.White)
                }else{
                    this
                }
            }
    ) {

    }
}