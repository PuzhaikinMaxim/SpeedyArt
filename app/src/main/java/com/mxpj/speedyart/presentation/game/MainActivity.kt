package com.mxpj.speedyart.presentation.game

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.drawable.toBitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.mxpj.speedyart.R
import com.mxpj.speedyart.presentation.ImageToPictureClassParser
import com.mxpj.speedyart.ui.theme.SpeedyArtTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val parser = ImageToPictureClassParser()
        //parser.parseToPicture(resources.getDrawable(R.drawable.heart_test,null).toBitmap())
        val resource = BitmapFactory.decodeResource(
            resources,
            R.drawable.heart_test,
            parser.getBitmapFactoryOptions()
        )
        val picture = parser.parseToPicture(BitmapFactory.decodeResource(
            resources,
            R.drawable.heart_test,
            parser.getBitmapFactoryOptions()
        ))
        val bitmap = PictureDrawer().getPictureBitmap(picture, 600)
        setContent {
            SpeedyArtTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    /*
                    Image(
                        bitmap.asImageBitmap(),
                        "test",
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxWidth(),
                        filterQuality = FilterQuality.None
                    )
                    
                     */
                    Column(

                    ) {
                        ZoomImage(bitmap = bitmap)
                        Greeting("Android")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(modifier = Modifier.height(50.dp), text = "Hello $name!")
}

@Composable
fun ZoomImage(bitmap: Bitmap) {
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var widthInPx by remember { mutableStateOf(0f) }
    val height = 600
    val width = 750
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .onGloballyPositioned { layoutCoordinates ->
                widthInPx = layoutCoordinates.size.width.toFloat()/2
            }
            .pointerInput(Unit) {
                forEachGesture {
                    awaitPointerEventScope {
                        awaitFirstDown()
                        do {
                            val event = awaitPointerEvent()
                            scale = calculateScale(scale, event.calculateZoom())
                            val offset = event.calculatePan()
                            offsetX += offset.x
                            offsetX = fitOffset(offsetX, widthInPx.toInt(), scale)
                            println("x: $offsetX")
                            offsetY += offset.y
                            offsetY = fitOffset(offsetY, widthInPx.toInt(), scale)
                            println("y: $offsetY")
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
                //.weight(1f)
                .aspectRatio(1f)
                .background(Color.Gray)
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
    println(offset)
    val scaleOffset = ((measurement - measurement * (1/scale)))*scale
    if(offset + scaleOffset < 0){
        return -scaleOffset
    }
    if(offset > scaleOffset){
        return scaleOffset
    }
    return offset
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpeedyArtTheme {
        Greeting("Android")
    }
}