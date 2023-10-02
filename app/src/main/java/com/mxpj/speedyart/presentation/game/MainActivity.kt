package com.mxpj.speedyart.presentation.game

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mxpj.speedyart.R
import com.mxpj.speedyart.domain.AppTheme
import com.mxpj.speedyart.domain.GameResult
import com.mxpj.speedyart.domain.Picture
import com.mxpj.speedyart.presentation.*
import com.mxpj.speedyart.presentation.navigation.Screen
import com.mxpj.speedyart.ui.theme.SpeedyArtTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val coords = mutableStateOf("text")
    private lateinit var picture: Picture
    private lateinit var viewModel: GameViewModel

    private val mainActivityViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val parser = ImageToPictureClassParser()
        //viewModel = ViewModelProvider(this,ViewModelFactory())[GameViewModel::class.java]
        picture = parser.parseToPicture(BitmapFactory.decodeResource(
            resources,
            R.drawable.heart,
            parser.getBitmapFactoryOptions()
        ))
        //viewModel.setPicture(picture)
        val bitmap = PictureDrawer(
            picture = picture,
            fitSize = 600
        ).getPictureBitmap()
        setContent {
            val theme by mainActivityViewModel.theme.observeAsState(AppTheme.LIGHT)
            SpeedyArtTheme(theme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = Screen.PACK_SELECTION_SCREEN.route) {
                        composable(Screen.PACK_SELECTION_SCREEN.route){
                            PicturePackSelectionScreen(navController)
                        }
                        composable(Screen.STATISTICS_SCREEN.route){
                            StatisticsScreen()
                        }
                        composable(Screen.PICTURE_SELECTION_SCREEN.route){
                            PictureSelectionScreen(navController)
                        }
                        composable(Screen.PICTURE_SCREEN.route){
                            PictureScreen()
                        }
                        composable(Screen.SETTINGS_SCREEN.route){
                            SettingsScreen(theme) {
                                mainActivityViewModel.changeTheme(it)
                            }
                        }
                    }
                    /*
                    Column {
                        ZoomImage(bitmap = bitmap)
                        //Greeting("Android")
                        //MistakesAmount()
                        ColorPalette(colors = picture.availablePalette, gameViewModel = viewModel, this@MainActivity)
                        Timer()
                        PlayerHealth(gameViewModel = viewModel, observer = this@MainActivity)
                    }
                    GameEndMessage()

                     */
                }
            }
        }
    }

    @Composable
    fun MistakesAmount() {
        var mistakes by remember { mutableStateOf("Количество ошибок: 0") }
        /*
        LaunchedEffect(Unit){
            viewModel.mistakesAmount.observe(this@MainActivity){
                mistakes = "Количество ошибок: $it"
            }
        }

         */
        Text(text = mistakes)
    }

    @Composable
    fun Greeting(name: String) {
        val c by coords
        Text(modifier = Modifier.height(50.dp), text = c)
    }

    @Composable
    fun Timer() {
        var timer by remember { mutableStateOf(0.0f) }
        var progress = remember { Animatable(0f) }
        val scope = rememberCoroutineScope()
        LaunchedEffect(Unit) {
            viewModel.shouldResetTimer.observe(this@MainActivity) {
                scope.launch {
                    startProgressBarAnimation(progress)
                }
            }
            startProgressBarAnimation(progress)
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
    fun GameEndMessage() {
        var gameEndMessage by remember { mutableStateOf("") }
        var isVisible by remember { mutableStateOf(false) }
        LaunchedEffect(Unit){
            viewModel.gameResult.observe(this@MainActivity){
                if(it == GameResult.GAME_WON){
                    gameEndMessage = "Поздравляем, вы победили!"
                    isVisible = true
                }
                else if(it == GameResult.GAME_LOST){
                    gameEndMessage = "Вы проиграли"
                    isVisible = true
                }
            }
        }
        if(isVisible){
            Text(
                text = gameEndMessage,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                fontSize = 40.sp,
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    fun ZoomImage(bitmap: Bitmap) {
        var scale by remember { mutableStateOf(1f) }
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }
        var widthInPx by remember { mutableStateOf(0f) }
        var image by remember { mutableStateOf(bitmap.asImageBitmap()) }
        LaunchedEffect(key1 = Unit, block = {
            viewModel.picture.observe(this@MainActivity){
                picture = it
                image = PictureDrawer(
                    picture,
                    viewModel.selectedColor.value,
                    600
                ).getPictureBitmap().asImageBitmap()
            }
        })
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
                            //var isMoved = false
                            var position = Offset.Zero
                            var firstDownTime = System.currentTimeMillis()
                            do {
                                val event = awaitPointerEvent()
                                event.changes.forEach { it.consumePositionChange() }
                                /*
                                if (event.calculatePan() != Offset.Zero && event.calculateZoom() != 0f) {
                                    isMoved = true
                                }

                                 */
                                position = event.changes[0].position
                            } while (event.changes.any { it.pressed })
                            val clickTime = System.currentTimeMillis() - firstDownTime
                            val isClickTimePassed = clickTime > 250
                            if (!isClickTimePassed) {
                                coords.value = position.toString()
                                val clickPosition = getClickedPixelPosition(
                                    Pair(offsetX, offsetY),
                                    Pair(position.x, position.y),
                                    calculateCellSize(widthInPx * 2, picture.gridCells.size),
                                    scale,
                                    Pair(picture.gridCells[0].size, picture.gridCells.size)
                                )
                                viewModel.onClick(viewModel.selectedColor.value, clickPosition)
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
                image,
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

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        SpeedyArtTheme {
            Greeting("Android")
        }
    }
}

