package com.mxpj.speedyart.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mxpj.speedyart.R
import com.mxpj.speedyart.domain.model.GameResult
import com.mxpj.speedyart.presentation.game.GameViewModel
import com.mxpj.speedyart.presentation.utils.observeStateChange
import com.mxpj.speedyart.ui.theme.SpeedyArtTheme

@Composable
fun GameStartModal(gameViewModel: GameViewModel) {
    val timer by gameViewModel.gameCountdown.timer.observeAsState()
    NonInteractiveBackground {
        Column(modifier = Modifier
            .fillMaxWidth(0.9f)
            .widthIn(max = 500.dp)
            .heightIn(min = 150.dp)
            .background(SpeedyArtTheme.colors.primary, shape = RoundedCornerShape(15.dp))
            .padding(10.dp)) {
            TextBig(
                text = stringResource(R.string.game_start_modal_text),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Timer(
                text = timer.toString(),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun GameEndModal(gameViewModel: GameViewModel) {
    var gameEndText by remember { mutableStateOf("") }
    val context = LocalContext.current
    gameViewModel.gameResult.observeStateChange {
        if(it == GameResult.GAME_LOST){
            gameEndText = context.getString(R.string.game_end_modal_text_lost)
        }
        else if(it == GameResult.GAME_WON){
            gameEndText = context.getString(R.string.game_end_modal_text_won)
        }
    }
    NonInteractiveBackground {
        Column(modifier = Modifier
            .fillMaxWidth(0.9f)
            .widthIn(max = 500.dp)
            .heightIn(min = 150.dp)
            .background(SpeedyArtTheme.colors.primary, shape = RoundedCornerShape(15.dp))
            .padding(10.dp)) {
            TextBig(
                text = gameEndText,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            TextNormal(
                text = "Время: ",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            TextNormal(
                text = "Количество ошибок: ",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(20.dp))
            GameModalButton(text = "Заново", modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(135.dp)) {

            }
            Spacer(modifier = Modifier.height(10.dp))
            GameModalButton(text = "Закончить", modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(135.dp)) {

            }
        }
    }
}

@Composable
fun NonInteractiveBackground( content: @Composable BoxScope.() -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .clickable(
                onClick = {},
                interactionSource = interactionSource,
                indication = null
            )
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun GameModalButton(text: String, modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = SpeedyArtTheme.colors.onPrimary),
        modifier = modifier
    ) {
        TextNormal(text = text, modifier = Modifier)
    }
}

@Composable
fun Timer(text: String, modifier: Modifier) {
    Text(
        text = text,
        fontSize = 104.sp,
        fontFamily = FontFamily.Silver,
        modifier = modifier,
        color = SpeedyArtTheme.colors.text
    )
}

@Composable
fun TextBig(text: String, modifier: Modifier) {
    Text(
        text = text,
        fontSize = 42.sp,
        fontFamily = FontFamily.Silver,
        modifier = modifier,
        color = SpeedyArtTheme.colors.text
    )
}

@Composable
fun TextNormal(text: String, modifier: Modifier) {
    Text(
        text = text,
        fontSize = 32.sp,
        fontFamily = FontFamily.Silver,
        modifier = modifier,
        color = SpeedyArtTheme.colors.text
    )
}