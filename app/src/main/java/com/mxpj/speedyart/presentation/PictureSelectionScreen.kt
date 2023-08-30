package com.mxpj.speedyart.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mxpj.speedyart.R
import com.mxpj.speedyart.domain.PictureCompletion
import com.mxpj.speedyart.ui.theme.ProgressBarBackground
import com.mxpj.speedyart.ui.theme.ProgressYellow

@Preview
@Composable
fun PictureSelectionScreen() {
    val pictureCompletion = listOf(
        PictureCompletion(R.drawable.heart, listOf(
            LevelEasy(DifficultyStatus.PERFECT),
            LevelMedium(DifficultyStatus.COMPLETED),
            LevelHard(DifficultyStatus.UNLOCKED)
        ))
    )
    Column() {
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(pictureCompletion){
                PictureCard(it)
            }
        }
    }
}

@Composable
fun PictureCard(pictureCompletion: PictureCompletion) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(Color.LightGray)
            .padding(10.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            PictureProgressBar()
            Spacer(modifier = Modifier.height(15.dp))
            Difficulties(pictureCompletion.difficulties)
        }
        Image(
            bitmap = PixelImageProvider.getPixelImageBitmap(pictureCompletion.pictureResource),
            contentDescription = "",
            filterQuality = FilterQuality.None,
            modifier = Modifier
                .height(100.dp)
                .aspectRatio(1f)
        )
    }
}

@Composable
fun Difficulties(list: List<DifficultyLevel>) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
        for(difficulty in list) {
            Difficulty(difficulty)
            Spacer(modifier = Modifier.width(15.dp))
        }
    }
}

@Preview
@Composable
fun Difficulty(difficultyLevel: DifficultyLevel = LevelMedium(DifficultyStatus.UNLOCKED)) {
    if(
        difficultyLevel.status == DifficultyStatus.LOCKED ||
        difficultyLevel.status == DifficultyStatus.UNLOCKED
    ) return
    Box(
        Modifier
            .height(50.dp)
            .width(50.dp)
            .background(color = difficultyLevel.color)
    ) {
        when(difficultyLevel.status){
            DifficultyStatus.COMPLETED -> {
                Image(
                    painter = painterResource(R.drawable.ic_checkmark),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Color.Black),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .height(25.dp)
                )
            }
            DifficultyStatus.PERFECT -> {
                Image(
                    bitmap = PixelImageProvider.getPixelImageBitmap(R.drawable.ic_trophy),
                    contentDescription = "",
                    filterQuality = FilterQuality.None,
                    modifier = Modifier
                        .height(25.dp)
                        .aspectRatio(1f)
                        .align(Alignment.Center),
                )
            }
            else -> {}
        }
    }
}

@Preview
@Composable
fun PictureProgressBar(
    progress: Float = .5f,
    progressInPercent: Int = 50
) {
    Box() {
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .height(30.dp)
                .clip(CircleShape)
                .fillMaxWidth(0.9f),
            color = ProgressYellow,
            backgroundColor = ProgressBarBackground
        )
        Image(
            painter = painterResource(R.drawable.ic_checkmark),
            contentDescription = "",
            colorFilter = ColorFilter.tint(Color.Green),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 5.dp)
                .height(25.dp)
        )
        Text(
            text = stringResource(R.string.percents, progressInPercent),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 5.dp),
            fontFamily = FontFamily.getSilverFont(),
            fontSize = 20.sp
        )
    }
}