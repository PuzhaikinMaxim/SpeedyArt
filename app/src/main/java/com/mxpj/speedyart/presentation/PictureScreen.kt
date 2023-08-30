package com.mxpj.speedyart.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mxpj.speedyart.R

@Composable
@Preview
fun PictureScreen() {
    Scaffold() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Picture()
            Spacer(modifier = Modifier.height(15.dp))
            PictureStats()
            Spacer(modifier = Modifier.height(20.dp))
            DifficultyLevels()
        }
    }
}

@Composable
fun Picture() {
    val bitmap = PixelImageProvider.getPixelImageBitmap(R.drawable.ic_health)
    Image(
        bitmap,
        contentDescription = "",
        modifier = Modifier
            .fillMaxWidth(0.65f)
            .aspectRatio(1f),
        filterQuality = FilterQuality.None
    )
}

@Composable
fun PictureStats() {
    Column(Modifier.fillMaxWidth(0.8f)) {
        PictureStatText("Лучшее время: 5 мин 6 сек")
        PictureStatText("Размер: 64 x 64")
        PictureStatText("Клетки для закраски: 180")
    }
}

@Composable
fun DifficultyLevels() {
    Column() {
        DifficultyLevelButton(LevelHard(DifficultyStatus.LOCKED))
        Spacer(modifier = Modifier.height(10.dp))
        DifficultyLevelButton(LevelMedium(DifficultyStatus.UNLOCKED))
        Spacer(modifier = Modifier.height(10.dp))
        DifficultyLevelButton(LevelEasy(DifficultyStatus.PERFECT))
    }
}

@Composable
fun PictureStatText(
    text: String
) {
    Text(
        text = text,
        fontFamily = FontFamily.getSilverFont(),
        fontSize = 32.sp,
        textAlign = TextAlign.Start,
    )
}