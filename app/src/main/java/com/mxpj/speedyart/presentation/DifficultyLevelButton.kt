package com.mxpj.speedyart.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mxpj.speedyart.R

@Composable
fun DifficultyLevelButton(difficultyLevel: DifficultyLevel) {
    when(difficultyLevel.status){
        DifficultyStatus.LOCKED -> DifficultyLockedButton(difficultyLevel)
        DifficultyStatus.UNLOCKED -> DifficultyUnlockedButton(difficultyLevel)
        DifficultyStatus.COMPLETED -> DifficultyCompletedButton(difficultyLevel)
        DifficultyStatus.PERFECT -> DifficultyPerfectButton(difficultyLevel)
    }
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
fun DifficultyLockedButton(difficultyLevel: DifficultyLevel) {
    BaseDifficultyButton(
        backgroundColor = Color.Gray,
        difficultyNameResourceId = difficultyLevel.textResource
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            bitmap = PixelImageProvider.getPixelImageBitmap(R.drawable.ic_lock),
            contentDescription = "",
            filterQuality = FilterQuality.None,
            modifier = Modifier
                .height(30.dp)
                .aspectRatio(1f)
        )
    }
}

@Composable
fun DifficultyUnlockedButton(difficultyLevel: DifficultyLevel) {
    BaseDifficultyButton(
        backgroundColor = difficultyLevel.color,
        difficultyNameResourceId = difficultyLevel.textResource
    ) {
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun DifficultyCompletedButton(difficultyLevel: DifficultyLevel) {
    BaseDifficultyButton(
        backgroundColor = difficultyLevel.color,
        difficultyNameResourceId = difficultyLevel.textResource
    ) {
        Spacer(modifier = Modifier.width(10.dp))
        Image(
            painter = painterResource(R.drawable.ic_checkmark),
            contentDescription = "",
            modifier = Modifier.width(40.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun DifficultyPerfectButton(difficultyLevel: DifficultyLevel) {
    BaseDifficultyButton(
        backgroundColor = difficultyLevel.color,
        difficultyNameResourceId = difficultyLevel.textResource
    ) {
        Spacer(modifier = Modifier.width(10.dp))
        Image(
            painter = painterResource(R.drawable.ic_checkmark),
            contentDescription = "",
            modifier = Modifier.width(20.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.ic_trophy),
            contentDescription = "",
            modifier = Modifier.width(30.dp).aspectRatio(1f)
        )
    }
}