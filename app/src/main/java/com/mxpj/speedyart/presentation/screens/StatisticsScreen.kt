package com.mxpj.speedyart.presentation.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mxpj.speedyart.R
import com.mxpj.speedyart.presentation.Silver
import com.mxpj.speedyart.presentation.toIntInPercent
import com.mxpj.speedyart.presentation.viewmodels.StatisticsViewModel
import com.mxpj.speedyart.ui.theme.ProgressYellow
import com.mxpj.speedyart.ui.theme.SpeedyArtTheme

//@Preview
@Composable
fun StatisticsScreen(statisticsViewModel: StatisticsViewModel = hiltViewModel()) {
    Scaffold() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = SpeedyArtTheme.colors.background)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.completion),
                fontFamily = FontFamily.Silver,
                fontSize = 36.sp,
                color = SpeedyArtTheme.colors.text
            )
            Spacer(modifier = Modifier.height(10.dp))
            GeneralProgress(statisticsViewModel)
            Spacer(modifier = Modifier.height(30.dp))
            ConcreteProgresses(statisticsViewModel)
        }
    }
}

//@Preview
@Composable
fun GeneralProgress(statisticsViewModel: StatisticsViewModel) {
    val strokeWidth = 20.dp
    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Butt)
    }

    val totalProgress by statisticsViewModel.totalCompletion.observeAsState()

    val canvasColor = SpeedyArtTheme.colors.primary

    Box() {
        Canvas(modifier = Modifier
            .widthIn(max = 400.dp)
            .heightIn(max = 400.dp)
            .fillMaxWidth(0.6f)
            .aspectRatio(1f)) {
                val diameterOffset = stroke.width / 2
                drawCircle(
                    radius = size.minDimension / 2.0f - diameterOffset,
                    color = canvasColor, style = stroke
                )
        }
        Text(
            text = stringResource(
                R.string.percents,
                totalProgress?.totalCompletionPercent?.toIntInPercent() ?: 0
            ),
            modifier = Modifier.align(Alignment.Center),
            fontFamily = FontFamily.Silver,
            fontSize = 42.sp,
            color = SpeedyArtTheme.colors.text
        )
        CircularProgressIndicator(
            progress = totalProgress?.totalCompletionPercent ?: 0f,
            strokeWidth = strokeWidth,
            color = SpeedyArtTheme.colors.progressBar,
            modifier = Modifier
                .widthIn(max = 400.dp)
                .heightIn(max = 400.dp)
                .fillMaxWidth(0.6f)
                .aspectRatio(1f)
        )
    }
}

//@Preview
@Composable
fun ConcreteProgresses(statisticsViewModel: StatisticsViewModel) {
    val progress by statisticsViewModel.totalCompletion.observeAsState()
    Column(modifier = Modifier.fillMaxWidth(0.8f)) {
        ConcreteProgress(
            text = stringResource(
                R.string.amount_pictures_completed,
                progress?.completedAmount ?: 0,
                progress?.total ?: 0
            ),
            progress = progress?.completedPercent ?: 0f,
            progressInPercents = progress?.completedPercent?.toIntInPercent() ?: 0
        )
        Spacer(modifier = Modifier.height(10.dp))
        ConcreteProgress(
            text = stringResource(
                R.string.amount_trophies,
                progress?.perfectAmount ?: 0,
                progress?.total ?: 0
            ),
            progress = progress?.perfectPercent ?: 0f,
            progressInPercents = progress?.perfectPercent?.toIntInPercent() ?: 0
        )
    }
}

@Composable
fun ConcreteProgress(
    text: String,
    progress: Float,
    progressInPercents: Int
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = text,
            fontSize = 32.sp,
            fontFamily = FontFamily.Silver,
            color = SpeedyArtTheme.colors.text
        )
        Box() {
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .clip(CircleShape),
                color = SpeedyArtTheme.colors.progressBar,
                backgroundColor = SpeedyArtTheme.colors.primary
            )
            Text(
                text = stringResource(R.string.percents, progressInPercents),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 5.dp),
                fontFamily = FontFamily.Silver,
                fontSize = 28.sp,
                color = SpeedyArtTheme.colors.text
            )
        }
    }
}


