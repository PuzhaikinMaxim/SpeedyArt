package com.mxpj.speedyart.presentation.screens

import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mxpj.speedyart.R
import com.mxpj.speedyart.domain.model.AppTheme
import com.mxpj.speedyart.presentation.CustomSwitch
import com.mxpj.speedyart.presentation.Silver
import com.mxpj.speedyart.presentation.utils.observeStateChange
import com.mxpj.speedyart.presentation.viewmodels.SettingsViewModel
import com.mxpj.speedyart.ui.theme.SpeedyArtTheme

//@Preview
@Composable
fun SettingsScreen(
    appTheme: AppTheme = AppTheme.LIGHT,
    onChangeThemeClick: (Boolean) -> Unit = {},
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    var isOpened by remember { mutableStateOf(false) }

    val context = LocalContext.current

    settingsViewModel.progressReset.observeStateChange {
        Toast.makeText(context, R.string.reset_progress_toast, Toast.LENGTH_LONG).show()
    }

    val onResetClick = {
        isOpened = !isOpened
    }

    val onNoOptionListener = {
        isOpened = false
    }

    val onYesOptionListener = {
        settingsViewModel.resetProgress()
        isOpened = false
    }

    Scaffold() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(SpeedyArtTheme.colors.background)
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                DarkModeSetting(appTheme, onChangeThemeClick)
                Spacer(modifier = Modifier.height(20.dp))
                ResetProgressSetting(onResetClick)
            }
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                BottomResetProgressModal(isOpened, onYesOptionListener, onNoOptionListener)
            }
        }
    }
}

@Composable
fun BottomResetProgressModal(
    isOpened: Boolean,
    onYesOptionClick: () -> Unit,
    onNoOptionClick: () -> Unit
) {
    val progressSlideAnimation by animateDpAsState(
        targetValue = if(isOpened) 200.dp else 0.dp,
        animationSpec = tween(durationMillis = 700)
    )
    
    Column(modifier = Modifier
        .fillMaxWidth()
        .clip(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
        .height(progressSlideAnimation)
        .background(SpeedyArtTheme.colors.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(R.string.reset_progress_question),
            color = SpeedyArtTheme.colors.text,
            fontSize = 34.sp,
            fontFamily = FontFamily.Silver
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            QuestionButton(stringResource(R.string.yes)){
                onYesOptionClick()
            }
            Spacer(modifier = Modifier.width(20.dp))
            QuestionButton(stringResource(R.string.no)){
                onNoOptionClick()
            }
        }
    }
}

@Composable
fun QuestionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = SpeedyArtTheme.colors.onPrimary)
    ) {
        Text(
            text = text,
            color = SpeedyArtTheme.colors.text,
            fontSize = 26.sp,
            fontFamily = FontFamily.Silver
        )
    }
}

@Composable
fun DarkModeSetting(
    appTheme: AppTheme = AppTheme.LIGHT,
    onChangeThemeClick: (Boolean) -> Unit = {}
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            stringResource(R.string.dark_mode_setting),
            fontFamily = FontFamily.Silver,
            fontSize = 30.sp,
            color = SpeedyArtTheme.colors.text
        )
        Spacer(modifier = Modifier.height(10.dp))
        DarkModeButton(appTheme, onChangeThemeClick)
    }
}

@Composable
fun DarkModeButton(
    appTheme: AppTheme = AppTheme.LIGHT,
    onChangeThemeClick: (Boolean) -> Unit = {}
) {
    var isDarkModeOn by remember { mutableStateOf(appTheme == AppTheme.DARK) }
    CustomSwitch(
        checked = isDarkModeOn,
        thumbSize = 25.dp,
        height = 30.dp,
        width = 80.dp,
        strokeWidth = 0.dp
    ){
        isDarkModeOn = !isDarkModeOn
        onChangeThemeClick(isDarkModeOn)
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
            fontSize = 30.sp,
            color = SpeedyArtTheme.colors.text
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
        colors = ButtonDefaults.buttonColors(backgroundColor = SpeedyArtTheme.colors.primary)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_restart_progress),
            contentDescription = "",
            colorFilter = run {
                if(SpeedyArtTheme.theme == AppTheme.DARK) ColorFilter.tint(Color.White) else null
            }
        )
    }
}