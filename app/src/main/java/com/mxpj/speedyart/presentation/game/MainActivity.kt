package com.mxpj.speedyart.presentation.game

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.mxpj.speedyart.R
import com.mxpj.speedyart.domain.Picture
import com.mxpj.speedyart.presentation.ImageToPictureClassParser
import com.mxpj.speedyart.ui.theme.SpeedyArtTheme

class MainActivity : ComponentActivity() {

    private val coords = mutableStateOf("text")
    private lateinit var picture: Picture

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val parser = ImageToPictureClassParser()
        picture = parser.parseToPicture(BitmapFactory.decodeResource(
            resources,
            R.drawable.heart_test,
            parser.getBitmapFactoryOptions()
        ))
        val bitmap = PictureDrawer().getPictureBitmap(picture, 600)
        setContent {
            SpeedyArtTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(

                    ) {
                        ZoomImage(bitmap = bitmap)
                        Greeting("Android")
                    }
                }
            }
        }
    }

    @Composable
    fun Greeting(name: String) {
        val c by coords
        Text(modifier = Modifier.height(50.dp), text = c)
    }

    @Composable
    fun ZoomImage(bitmap: Bitmap) {
        var scale by remember { mutableStateOf(1f) }
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }
        var widthInPx by remember { mutableStateOf(0f) }
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
                            var isMoved = false
                            var position = Offset.Zero
                            do {
                                val event = awaitPointerEvent()
                                event.changes.forEach { it.consumePositionChange() }
                                if (event.calculatePan() != Offset.Zero && event.calculateZoom() != 0f) {
                                    isMoved = true
                                }
                                position = event.changes[0].position
                            } while (event.changes.any { it.pressed })
                            if (!isMoved) {
                                coords.value = position.toString()
                                val clickPosition = getClickedPixelPosition(
                                    Pair(offsetX, offsetY),
                                    Pair(position.x, position.y),
                                    calculateCellSize(widthInPx * 2, picture.gridCells.size),
                                    scale,
                                    picture.gridCells.size
                                )
                                println(clickPosition)
                                println(position)
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
                                println(offsetX)
                                offsetX = fitOffset(offsetX, widthInPx.toInt(), scale)
                                offsetY += offset.y
                                println(offsetY)
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
                bitmap.asImageBitmap(),
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
        cellsAmount: Int
    ): Pair<Int, Int> {
        val size = cellsAmount * cellSize

        val offsetX = -offset.first / scale
        val offsetY = -offset.second / scale

        val initialOffsetX = (size - size * (1/scale))/2
        val initialOffsetY = initialOffsetX

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

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        SpeedyArtTheme {
            Greeting("Android")
        }
    }
}

