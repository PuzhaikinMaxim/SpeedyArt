package com.mxpj.speedyart.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mxpj.speedyart.R
import com.mxpj.speedyart.domain.model.*
import com.mxpj.speedyart.presentation.*
import com.mxpj.speedyart.presentation.viewmodels.PictureViewModel
import com.mxpj.speedyart.ui.theme.SpeedyArtTheme

@Composable
//@Preview
fun PictureScreen(
    pictureViewModel: PictureViewModel = hiltViewModel()
) {
    val pictureStatistics by pictureViewModel.pictureCompletion.observeAsState()

    Scaffold() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = SpeedyArtTheme.colors.background)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Picture(pictureStatistics?.pictureCompletion?.pictureAsset ?: R.drawable.heart_test)
            Spacer(modifier = Modifier.height(15.dp))
            PictureStats(pictureStatistics)
            Spacer(modifier = Modifier.height(20.dp))
            DifficultyLevels(
                pictureStatistics?.pictureCompletion?.difficulties ?: listOf()
            )
        }
    }
}

@Composable
fun Picture(resource: Int) {
    val bitmap = PixelImageProvider.getPixelImageBitmap(resource)
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
fun PictureStats(pictureStatistics: PictureStatistics?) {
    if(pictureStatistics == null) return
    Column(Modifier.fillMaxWidth(0.8f)) {
        if(pictureStatistics.time == null) {
            PictureStatText(stringResource(R.string.best_time_default))
        }
        else{
            PictureStatText(stringResource(
                R.string.best_time,
                pictureStatistics.time.first,
                pictureStatistics.time.second
            ))
        }
        PictureStatText(stringResource(
            R.string.size,
            pictureStatistics.size.first,
            pictureStatistics.size.second
        ))
        PictureStatText(stringResource(
            R.string.cells_to_fill,
            pictureStatistics.amountOfCells
        ))
        /*
        PictureStatText("Лучшее время: 5 мин 6 сек")
        PictureStatText("Размер: 64 x 64")
        PictureStatText("Клетки для закраски: 180")

         */
    }
}

@Composable
fun DifficultyLevels(difficultyLevels: List<DifficultyLevel>) {
    Column() {
        for(difficultyLevel in difficultyLevels.reversed()){
            DifficultyLevelButton(difficultyLevel)
            Spacer(modifier = Modifier.height(10.dp))
        }
        /*
        DifficultyLevelButton(LevelHard(DifficultyStatus.LOCKED))
        Spacer(modifier = Modifier.height(10.dp))
        DifficultyLevelButton(LevelMedium(DifficultyStatus.UNLOCKED))
        Spacer(modifier = Modifier.height(10.dp))
        DifficultyLevelButton(LevelEasy(DifficultyStatus.PERFECT))

         */
    }
}

@Composable
fun PictureStatText(
    text: String
) {
    Text(
        text = text,
        fontFamily = FontFamily.Silver,
        fontSize = 32.sp,
        textAlign = TextAlign.Start,
        color = SpeedyArtTheme.colors.text
    )
}