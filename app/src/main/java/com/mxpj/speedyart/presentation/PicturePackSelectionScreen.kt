package com.mxpj.speedyart.presentation

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mxpj.speedyart.domain.PicturePack
import com.mxpj.speedyart.ui.theme.ProgressYellow
import com.mxpj.speedyart.ui.theme.ProgressBarBackground

@Preview
@Composable
fun PicturePackSelectionScreen() {
    val lazyListState = rememberLazyListState()
    val list = mutableListOf<PicturePack>().apply {
        for(i in 0..100){
            add(PicturePack("Text",Pair(1,1),10,0.5f))
        }
    }
    Scaffold(
        topBar = {
            TopBar()
        }
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            state = lazyListState,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(list) {
                Spacer(modifier = Modifier.height(20.dp))
                PicturePackCard(picturePack = it)
            }
        }
    }
}

@Composable
fun PicturePackCard(picturePack: PicturePack) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color.LightGray)
            .padding(10.dp)
    ) {
        Text(
            text = picturePack.name,
            fontFamily = FontFamily.getSilverFont(),
            fontSize = 34.sp
        )
        Box() {
            LinearProgressIndicator(
                progress = picturePack.completionPercent,
                modifier = Modifier
                    .height(25.dp)
                    .clip(CircleShape)
                    .fillMaxWidth(),
                color = ProgressYellow,
                backgroundColor = ProgressBarBackground
            )
            Text(
                text = "${((picturePack.completionPercent*100)).toInt()}%",
                fontFamily = FontFamily.getSilverFont(),
                fontSize = 28.sp,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 10.dp)
            )
        }
        Row() {
            Text(
                text = "Размер: ${picturePack.size.first} x ${picturePack.size.second}",
                fontFamily = FontFamily.getSilverFont(),
                fontSize = 28.sp
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "${picturePack.amountOfImages} изображений",
                fontFamily = FontFamily.getSilverFont(),
                fontSize = 28.sp
            )
        }
    }
}