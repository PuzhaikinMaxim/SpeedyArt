package com.mxpj.speedyart.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mxpj.speedyart.domain.PicturePack
import com.mxpj.speedyart.presentation.Silver
import com.mxpj.speedyart.presentation.TopBar
import com.mxpj.speedyart.presentation.navigation.Screen
import com.mxpj.speedyart.ui.theme.ProgressYellow
import com.mxpj.speedyart.ui.theme.SpeedyArtTheme

//@Preview
@Composable
fun PicturePackSelectionScreen(navController: NavController) {
    val lazyListState = rememberLazyListState()
    val list = mutableListOf<PicturePack>().apply {
        for(i in 0..100){
            add(PicturePack("Text",Pair(1,1),10,0.5f))
        }
    }
    Scaffold(
        topBar = {
            TopBar(navController)
        }
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = SpeedyArtTheme.colors.background)
        ) {
            items(list) {
                Spacer(modifier = Modifier.height(20.dp))
                PicturePackCard(picturePack = it, navController)
            }
        }
    }
}

@Composable
fun PicturePackCard(
    picturePack: PicturePack,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .clip(shape = RoundedCornerShape(20.dp))
            .clickable { navController.navigate(Screen.PICTURE_SELECTION_SCREEN.route) }
            .background(color = SpeedyArtTheme.colors.primary)
            .padding(10.dp),
    ) {
        Text(
            text = picturePack.name,
            fontFamily = FontFamily.Silver,
            fontSize = 34.sp,
            color = SpeedyArtTheme.colors.text
        )
        Box() {
            LinearProgressIndicator(
                progress = picturePack.completionPercent,
                modifier = Modifier
                    .height(25.dp)
                    .clip(CircleShape)
                    .fillMaxWidth(),
                color = ProgressYellow,
                backgroundColor = SpeedyArtTheme.colors.progressBarBackground
            )
            Text(
                text = "${((picturePack.completionPercent*100)).toInt()}%",
                fontFamily = FontFamily.Silver,
                fontSize = 28.sp,
                color = SpeedyArtTheme.colors.text,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 10.dp)
            )
        }
        Row() {
            Text(
                text = "Размер: ${picturePack.size.first} x ${picturePack.size.second}",
                fontFamily = FontFamily.Silver,
                fontSize = 28.sp,
                color = SpeedyArtTheme.colors.text
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "${picturePack.amountOfImages} изображений",
                fontFamily = FontFamily.Silver,
                fontSize = 28.sp,
                color = SpeedyArtTheme.colors.text
            )
        }
    }
}