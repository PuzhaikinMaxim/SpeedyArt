package com.mxpj.speedyart.presentation.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mxpj.speedyart.domain.model.GameResult
import com.mxpj.speedyart.domain.model.Picture
import com.mxpj.speedyart.presentation.game.ColorPalette
import com.mxpj.speedyart.presentation.game.GameViewModel
import com.mxpj.speedyart.presentation.game.PlayerHealth
import com.mxpj.speedyart.presentation.utils.observeStateChange
import kotlinx.coroutines.launch

@Composable
fun GameScreen(
    navController: NavController,
    gameViewModel: GameViewModel = hiltViewModel()
) {
    val picture by gameViewModel.picture.observeAsState()

    Column {
        ZoomImage(gameViewModel, picture!!)
        //Greeting("Android")
        //MistakesAmount()
        ColorPalette(colors = picture!!.availablePalette, gameViewModel = gameViewModel)
        Timer(gameViewModel)
        PlayerHealth(gameViewModel = gameViewModel)
    }
    GameEndMessage(gameViewModel)
}

@Composable
fun Timer(gameViewModel: GameViewModel) {
    var timer by remember { mutableStateOf(0.0f) }
    var progress = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    gameViewModel.shouldResetTimer.observeStateChange {
        scope.launch {
            startProgressBarAnimation(progress)
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
    LinearProgressIndicator(
        progress = progress.value,
        modifier = Modifier
            .height(20.dp)
            .fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(10.dp))
}

private suspend fun startProgressBarAnimation(animatable: Animatable<Float, AnimationVector1D>) {
    animatable.snapTo(0f)
    animatable.animateTo(
        targetValue = 1f,
        animationSpec = tween(GameViewModel.EIGHT_SECONDS.toInt(), easing = LinearEasing)
    )
}

@Composable
fun GameEndMessage(gameViewModel: GameViewModel) {
    val gameResult by gameViewModel.gameResult.observeAsState()
    if(gameResult != GameResult.GAME_CONTINUING){
        Text(
            text = stringResource(gameResult!!.msg!!),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            fontSize = 40.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ZoomImage(gameViewModel: GameViewModel, picture: Picture) {
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var widthInPx by remember { mutableStateOf(0f) }
    val image by gameViewModel.pictureBitmap.observeAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .onGloballyPositioned { layoutCoordinates ->
                widthInPx = layoutCoordinates.size.width.toFloat() / 2
            }
            .pointerInput(Unit) {
                forEachGesture {
                    awaitPointerEventScope {
                        awaitFirstDown()
                        var position = Offset.Zero
                        var firstDownTime = System.currentTimeMillis()
                        do {
                            val event = awaitPointerEvent()
                            event.changes.forEach { it.consumePositionChange() }
                            position = event.changes[0].position
                        } while (event.changes.any { it.pressed })
                        val clickTime = System.currentTimeMillis() - firstDownTime
                        val isClickTimePassed = clickTime > 250
                        if (!isClickTimePassed) {
                            val clickPosition = getClickedPixelPosition(
                                Pair(offsetX, offsetY),
                                Pair(position.x, position.y),
                                calculateCellSize(widthInPx * 2, picture.gridCells.size),
                                scale,
                                Pair(picture.gridCells[0].size, picture.gridCells.size)
                            )
                            gameViewModel.onClick(clickPosition)
                        }
                    }
                }
            }
            .pointerInput(Unit) {
                forEachGesture {
                    awaitPointerEventScope {
                        awaitFirstDown()
                        do {
                            val event = awaitPointerEvent()
                            scale = calculateScale(scale, event.calculateZoom())
                            val offset = event.calculatePan()
                            event.calculateCentroid()
                            offsetX += offset.x
                            offsetX = fitOffset(offsetX, widthInPx.toInt(), scale)
                            offsetY += offset.y
                            offsetY = fitOffset(offsetY, widthInPx.toInt(), scale)
                        } while (event.changes.any { it.pressed })
                    }
                }
            }
            .graphicsLayer {
                clip = true
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            image!!,
            "test",
            alignment = Alignment.TopCenter,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .border(1.dp, Color.Black)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offsetX
                    translationY = offsetY
                    clip = true
                },
            filterQuality = FilterQuality.None
        )
    }
}

private fun calculateScale(prevScale: Float, zoom: Float): Float {
    var scale = prevScale * zoom
    if(scale > 3.0f){
        scale = 3.0f
    }
    if(scale < 1.0f){
        scale = 1.0f
    }
    return scale
}

private fun fitOffset(offset: Float, measurement: Int, scale: Float): Float {
    val scaleOffset = ((measurement - measurement * (1/scale)))*scale
    if(offset + scaleOffset < 0){
        return -scaleOffset
    }
    if(offset > scaleOffset){
        return scaleOffset
    }
    return offset
}

private fun getClickedPixelPosition(
    offset: Pair<Float, Float>,
    clickPosition: Pair<Float, Float>,
    cellSize: Int,
    scale: Float,
    cellsAmount: Pair<Int, Int>
): Pair<Int, Int> {
    val sizeX = cellsAmount.first * cellSize
    val sizeY = cellsAmount.second * cellSize

    val offsetX = -offset.first / scale
    val offsetY = -offset.second / scale

    val initialOffsetX = (sizeX - sizeX * (1/scale))/2
    val initialOffsetY = (sizeY - sizeY * (1/scale))/2

    val appliedOffsetX = initialOffsetX + offsetX
    val appliedOffsetY = initialOffsetY + offsetY

    val pointX = (clickPosition.first / scale) + appliedOffsetX
    val pointY = (clickPosition.second / scale) + appliedOffsetY

    val positionX = (pointX / cellSize).toInt()
    val positionY = (pointY / cellSize).toInt()
    return Pair(positionX,positionY)
}

private fun calculateCellSize(measurement: Float, rowCellAmount: Int): Int {
    return (measurement / rowCellAmount).toInt()
}