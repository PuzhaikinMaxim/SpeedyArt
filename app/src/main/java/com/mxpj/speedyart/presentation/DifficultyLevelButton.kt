package com.mxpj.speedyart.presentation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DifficultyLevelButton(difficulty: Difficulty) {
    when(difficulty){
        is Difficulty.DifficultyLocked -> DifficultyLockedButton(difficulty)
        is Difficulty.DifficultyUnlocked -> DifficultyUnlockedButton(difficulty)
        is Difficulty.DifficultyCompleted -> DifficultyCompletedButton(difficulty)
        is Difficulty.DifficultyPerfect -> DifficultyPerfectButton(difficulty)
    }
    /*
    BaseDifficultyButton(
        backgroundColor = difficulty.difficultyLevel.color,
        difficultyNameResourceId = difficulty.difficultyLevel.textResource
    ) {
        Spacer(modifier = Modifier.weight(1f))
    }

     */
    /*
    Button(
        shape = RoundedCornerShape(5.dp),
        onClick = { /*TODO*/ },
        modifier = Modifier
            .height(45.dp)
            .fillMaxWidth(0.8f)
            .clip(RoundedCornerShape(5.dp)),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = difficulty.difficultyLevel.color,
        )
    ) {
        Text(
            text = stringResource(difficulty.difficultyLevel.textResource),
            fontFamily = FontFamily.getSilverFont(),
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.weight(1f))
    }

     */
}

@Composable
fun BaseDifficultyButton(
    backgroundColor: Color,
    difficultyNameResourceId: Int,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        shape = RoundedCornerShape(5.dp),
        onClick = { /*TODO*/ },
        modifier = Modifier
            .height(45.dp)
            .fillMaxWidth(0.8f)
            .clip(RoundedCornerShape(5.dp)),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
        )
    ) {
        Text(
            text = stringResource(difficultyNameResourceId),
            fontFamily = FontFamily.getSilverFont(),
            fontSize = 28.sp
        )
        content(this)
    }
}

@Composable
fun DifficultyLockedButton(difficulty: Difficulty) {
    BaseDifficultyButton(
        backgroundColor = difficulty.difficultyLevel.color,
        difficultyNameResourceId = difficulty.difficultyLevel.textResource
    ) {
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun DifficultyUnlockedButton(difficulty: Difficulty) {
    BaseDifficultyButton(
        backgroundColor = difficulty.difficultyLevel.color,
        difficultyNameResourceId = difficulty.difficultyLevel.textResource
    ) {
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun DifficultyCompletedButton(difficulty: Difficulty) {
    BaseDifficultyButton(
        backgroundColor = difficulty.difficultyLevel.color,
        difficultyNameResourceId = difficulty.difficultyLevel.textResource
    ) {
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun DifficultyPerfectButton(difficulty: Difficulty) {
    BaseDifficultyButton(
        backgroundColor = difficulty.difficultyLevel.color,
        difficultyNameResourceId = difficulty.difficultyLevel.textResource
    ) {
        Spacer(modifier = Modifier.weight(1f))
    }
}