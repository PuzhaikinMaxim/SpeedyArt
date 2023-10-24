package com.mxpj.speedyart.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mxpj.speedyart.ui.theme.SpeedyArtTheme

@Preview
@Composable
fun GameStartModal() {
    Column(modifier = Modifier
        .fillMaxWidth(0.7f)
        .widthIn(max = 500.dp)
        .heightIn(min = 150.dp)
        .background(SpeedyArtTheme.colors.primary, shape = RoundedCornerShape(15.dp))
        .padding(10.dp)) {
        TextBig(
            text = "Игра начнется через",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Timer(
            text = "1",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Preview
@Composable
fun GameEndModal() {
    Column(modifier = Modifier
        .fillMaxWidth(0.7f)
        .widthIn(max = 500.dp)
        .heightIn(min = 150.dp)
        .background(SpeedyArtTheme.colors.primary, shape = RoundedCornerShape(15.dp))
        .padding(10.dp)) {
        TextBig(
            text = "Вы выиграли",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        TextNormal(
            text = "Время: ",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        TextNormal(
            text = "Количество ошибок: ",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Button(
            onClick = {  },
            modifier = Modifier
                .background(
                    color = SpeedyArtTheme.colors.onPrimary,
                    shape = RoundedCornerShape(10.dp)
                )
        ) {

        }
    }
}

@Composable
fun Timer(text: String, modifier: Modifier) {
    Text(
        text = text,
        fontSize = 104.sp,
        fontFamily = FontFamily.Silver,
        modifier = modifier,
        color = SpeedyArtTheme.colors.text
    )
}

@Composable
fun TextBig(text: String, modifier: Modifier) {
    Text(
        text = text,
        fontSize = 42.sp,
        fontFamily = FontFamily.Silver,
        modifier = modifier,
        color = SpeedyArtTheme.colors.text
    )
}

@Composable
fun TextNormal(text: String, modifier: Modifier) {
    Text(
        text = text,
        fontSize = 32.sp,
        fontFamily = FontFamily.Silver,
        modifier = modifier,
        color = SpeedyArtTheme.colors.text
    )
}