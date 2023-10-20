package com.mxpj.speedyart.presentation.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.mxpj.speedyart.R
import com.mxpj.speedyart.presentation.PixelImageProvider

@Composable
fun PlayerHealth(
    gameViewModel: GameViewModel
) {
    val healthAmount by gameViewModel.healthAmount.observeAsState()
    
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        for(i in 1..healthAmount!!){
            HealthImage()
            Spacer(modifier = Modifier
                .fillMaxHeight()
                .width(10.dp))
        }
    }
}

@Composable
fun HealthImage() {
    val bitmap = PixelImageProvider.getPixelImageBitmap(R.drawable.ic_health)
    Image(
        bitmap,
        "Здоровье",
        modifier = Modifier
            .height(50.dp),
        contentScale = ContentScale.FillHeight,
        filterQuality = FilterQuality.None
    )
}