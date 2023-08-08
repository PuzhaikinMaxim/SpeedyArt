package com.mxpj.speedyart.presentation.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun StatisticsScreen() {
    Scaffold() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            generalProgress()
        }
    }
}

@Preview
@Composable
fun generalProgress() {

    Spacer(modifier = Modifier.height(30.dp))
    val stroke = with(LocalDensity.current) {
        Stroke(width = 15.dp.toPx(), cap = StrokeCap.Butt)
    }

    Box() {
        Canvas(modifier = Modifier
            .fillMaxWidth(0.6f)
            .aspectRatio(1f)) {
            val diameterOffset = stroke.width / 2
            drawCircle(
                radius = size.minDimension / 2.0f - diameterOffset,
                color = LightGray, style = stroke
            )
        }
        CircularProgressIndicator(
            progress = 0.8f,
            strokeWidth = 15.dp,
            color = Color.Green,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .aspectRatio(1f)
        )
    }

}