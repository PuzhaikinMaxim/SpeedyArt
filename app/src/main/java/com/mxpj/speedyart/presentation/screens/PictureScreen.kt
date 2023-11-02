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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mxpj.speedyart.R
import com.mxpj.speedyart.domain.model.*
import com.mxpj.speedyart.presentation.*
import com.mxpj.speedyart.presentation.viewmodels.PictureViewModel
import com.mxpj.speedyart.ui.theme.SpeedyArtTheme

@Composable
//@Preview
fun PictureScreen(
    navController: NavController,
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
            Picture(pictureStatistics?.picture?.pictureAsset ?: R.drawable.heart_test)
            Spacer(modifier = Modifier.height(15.dp))
            PictureStats(pictureStatistics)
            Spacer(modifier = Modifier.height(20.dp))
            DifficultyLevels(
                pictureStatistics?.picture?.difficulties ?: listOf(),
                navController
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
fun PictureStats(pictureWithStatistics: PictureWithStatistics?) {
    if(pictureWithStatistics == null) return
    Column(Modifier.fillMaxWidth(0.8f)) {
        if(pictureWithStatistics.time == null) {
            PictureStatText(stringResource(R.string.best_time_default))
        }
        else{
            PictureStatText(stringResource(
                R.string.best_time,
                pictureWithStatistics.time.first,
                pictureWithStatistics.time.second
            ))
        }
        PictureStatText(stringResource(
            R.string.size,
            pictureWithStatistics.size.first,
            pictureWithStatistics.size.second
        ))
        PictureStatText(stringResource(
            R.string.cells_to_fill,
            pictureWithStatistics.amountOfCells
        ))
    }
}

@Composable
fun DifficultyLevels(difficultyLevels: List<DifficultyLevel>, navController: NavController) {
    Column() {
        for(difficultyLevel in difficultyLevels.reversed()){
            DifficultyLevelButton(difficultyLevel, navController = navController)
            Spacer(modifier = Modifier.height(10.dp))
        }
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