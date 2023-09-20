package com.mxpj.speedyart.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mxpj.speedyart.ui.theme.DarkSky

@Preview
@Composable
fun SettingsScreen() {
    Scaffold() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            DarkModeSetting()
            Spacer(modifier = Modifier.height(10.dp))
            FontSetting()
            Spacer(modifier = Modifier.height(10.dp))
            ResetProgressSetting()
        }
    }
}

@Composable
fun DarkModeSetting() {
    DarkModeButton()
}

@Composable
fun DarkModeButton(initialMode: Boolean = false) {
    var isDarkModeOn by remember { mutableStateOf(initialMode) }
    Switch(
        checked = isDarkModeOn,
        onCheckedChange = {isDarkModeOn = !isDarkModeOn},
        colors = SwitchDefaults.colors(
            checkedTrackColor = DarkSky,
            checkedTrackAlpha = 1f,
            checkedThumbColor = Color.Gray
        ),
        modifier = Modifier.padding(5.dp)
    )
}

@Composable
fun FontSetting() {

}

@Composable
fun ResetProgressSetting() {

}