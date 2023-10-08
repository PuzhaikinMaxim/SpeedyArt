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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mxpj.speedyart.R
import com.mxpj.speedyart.presentation.Silver
import com.mxpj.speedyart.ui.theme.ProgressYellow
import com.mxpj.speedyart.ui.theme.SpeedyArtTheme

@Preview
@Composable
fun StatisticsScreen() {
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
            GeneralProgress()
            Spacer(modifier = Modifier.height(30.dp))
            ConcreteProgresses()
        }
    }
}

@Preview
@Composable
fun GeneralProgress() {
    val strokeWidth = 20.dp
    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Butt)
    }

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
            text = "70%",
            modifier = Modifier.align(Alignment.Center),
            fontFamily = FontFamily.Silver,
            fontSize = 42.sp,
            color = SpeedyArtTheme.colors.text
        )
        CircularProgressIndicator(
            progress = 0.8f,
            strokeWidth = strokeWidth,
            color = ProgressYellow,
            modifier = Modifier
                .widthIn(max = 400.dp)
                .heightIn(max = 400.dp)
                .fillMaxWidth(0.6f)
                .aspectRatio(1f)
        )
    }
}

@Preview
@Composable
fun ConcreteProgresses() {
    Column(modifier = Modifier.fillMaxWidth(0.8f)/*.background(Gray)*/) {
        ConcreteProgress(
            text = stringResource(R.string.amount_pictures_completed, 5, 10),
            progress = 0.5f,
            progressInPercents = 50
        )
        Spacer(modifier = Modifier.height(10.dp))
        ConcreteProgress(
            text = stringResource(R.string.amount_stars, 5, 10),
            progress = 0.5f,
            progressInPercents = 50
        )
        Spacer(modifier = Modifier.height(10.dp))
        ConcreteProgress(
            text = stringResource(R.string.amount_trophies, 5, 10),
            progress = 1.0f,
            progressInPercents = 100
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
                color = ProgressYellow,
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


