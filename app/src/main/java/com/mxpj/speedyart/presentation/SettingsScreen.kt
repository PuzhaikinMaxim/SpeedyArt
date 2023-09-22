package com.mxpj.speedyart.presentation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mxpj.speedyart.R

@Preview
@Composable
fun SettingsScreen() {
    var isOpened by remember { mutableStateOf(false) }

    val onResetClick = {
        isOpened = !isOpened
    }

    Scaffold() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                DarkModeSetting()
                Spacer(modifier = Modifier.height(20.dp))
                ResetProgressSetting(onResetClick)
            }
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                BottomResetProgressModal(isOpened)
            }
        }
    }
}

@Composable
fun BottomResetProgressModal(isOpened: Boolean) {
    
    val progressSlideAnimation by animateDpAsState(
        targetValue = if(isOpened) 200.dp else 0.dp,
        animationSpec = tween(durationMillis = 700)
    )
    
    Column(modifier = Modifier
        .fillMaxWidth()
        .clip(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
        .height(progressSlideAnimation)
        .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.reset_progress_question),
            color = Color.Black,
            fontSize = 34.sp,
            fontFamily = FontFamily.Silver
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            QuestionButton(stringResource(R.string.yes)){}
            Spacer(modifier = Modifier.width(20.dp))
            QuestionButton(stringResource(R.string.no)){}
        }
    }
}

@Composable
fun QuestionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontSize = 26.sp,
            fontFamily = FontFamily.Silver
        )
    }
}

@Composable
fun DarkModeSetting() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            stringResource(R.string.dark_mode_setting),
            fontFamily = FontFamily.Silver,
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        DarkModeButton()
    }
}

@Composable
fun DarkModeButton(initialMode: Boolean = false) {
    var isDarkModeOn by remember { mutableStateOf(initialMode) }
    CustomSwitch(
        checked = isDarkModeOn,
        thumbSize = 25.dp,
        height = 30.dp,
        width = 80.dp,
        strokeWidth = 0.dp
    ){
        isDarkModeOn = !isDarkModeOn
    }
}

@Composable
fun ResetProgressSetting(onResetProgressClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.reset_progress_setting),
            fontFamily = FontFamily.Silver,
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        ResetProgressButton(onResetProgressClick)
    }
}

@Composable
fun ResetProgressButton(onResetProgressClick: () -> Unit) {
    Button(
        onClick = { onResetProgressClick() },
        modifier = Modifier
            .width(80.dp)
            .height(40.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_restart_progress),
            contentDescription = ""
        )
    }
}