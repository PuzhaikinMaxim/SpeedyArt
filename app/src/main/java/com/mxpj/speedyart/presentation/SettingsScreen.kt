package com.mxpj.speedyart.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    Scaffold() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            DarkModeSetting()
            Spacer(modifier = Modifier.height(20.dp))
            ResetProgressSetting()
        }
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
fun ResetProgressSetting() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            stringResource(R.string.reset_progress_setting),
            fontFamily = FontFamily.Silver,
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        ResetProgressButton()
    }
}

@Composable
fun ResetProgressButton() {
    Button(
        onClick = {},
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