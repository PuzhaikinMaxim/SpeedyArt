package com.mxpj.speedyart.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mxpj.speedyart.ui.theme.DarkSky

@Composable
fun CustomSwitch(
    checked: Boolean = false,
    width: Dp = 36.dp,
    height: Dp = 20.dp,
    gapSize: Dp = 4.dp,
    strokeWidth: Dp = 2.dp,
    thumbSize: Dp = 24.dp,
    strokeColor: Color = Color.Gray,
    checkedTrackColor: Color = DarkSky,
    uncheckedTrackColor: Color = Color.LightGray,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    val alignment by animateAlignmentAsState(if (checked) 1f else -1f)

    val trackColor by animateColorAsState(if(checked) checkedTrackColor else uncheckedTrackColor)
    
    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .run {
                if(strokeWidth != 0.dp){
                    border(strokeWidth, strokeColor, RoundedCornerShape(height))
                }
                else{
                    this
                }
            }
            .background(
                color = trackColor,
                shape = RoundedCornerShape(height)
            )
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) { onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(start = gapSize, end = gapSize)
                .fillMaxSize(),
            contentAlignment = alignment
        ){
            Box(
                modifier = Modifier
                    .size(thumbSize)
                    .clip(CircleShape)
                    .background(Color.White, CircleShape)
            )
        }
    }

}

@Preview
@Composable
private fun SwitchTest() {
    var checked by remember {
        mutableStateOf(false)
    }
    CustomSwitch(
        checked = checked,
        thumbSize = 30.dp,
        height = 40.dp,
        width = 90.dp,
        strokeWidth = 0.dp
    ){
        checked = !checked
    }
}

@Composable
private fun animateAlignmentAsState(
    targetBiasValue: Float
): State<BiasAlignment> {
    val bias by animateFloatAsState(targetBiasValue)
    return derivedStateOf { BiasAlignment(horizontalBias = bias, verticalBias = 0f) }
}