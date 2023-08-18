package com.mxpj.speedyart.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mxpj.speedyart.domain.PicturePack

@Preview
@Composable
fun PicturePackSelectionScreen() {
    val list = listOf(PicturePack("Text",Pair(1,1),10,0.5f))
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        items(list) {
            PicturePackCard(picturePack = it)
        }
    }
}

@Composable
fun PicturePackCard(picturePack: PicturePack) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .background(Color.LightGray)
    ) {
        Text(
            text = picturePack.name,
            fontFamily = FontFamily.getSilverFont(),
            fontSize = 32.sp
        )
        LinearProgressIndicator(progress = picturePack.completionPercent)
        Row() {
            Text(text = "Размер: ${picturePack.size.first} x ${picturePack.size.second}")
            Text(text = "${picturePack.amountOfImages} изображений")
        }
    }
}