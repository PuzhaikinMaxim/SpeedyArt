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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mxpj.speedyart.R
import com.mxpj.speedyart.data.database.DatabaseDataInserter
import com.mxpj.speedyart.domain.model.AppTheme
import com.mxpj.speedyart.domain.model.GameResult
import com.mxpj.speedyart.domain.model.Picture
import com.mxpj.speedyart.presentation.*
import com.mxpj.speedyart.presentation.navigation.Screen
import com.mxpj.speedyart.presentation.screens.*
import com.mxpj.speedyart.presentation.viewmodels.MainViewModel
import com.mxpj.speedyart.ui.theme.SpeedyArtTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val coords = mutableStateOf("text")
    private lateinit var picture: Picture
    private lateinit var viewModel: GameViewModel

    private val mainActivityViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var databaseDataInserter: DatabaseDataInserter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val parser = ImageToPictureClassParser()
        picture = parser.parseToPicture(BitmapFactory.decodeResource(
            resources,
            R.drawable.heart,
            parser.getBitmapFactoryOptions()
        ))
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
                            PictureScreen(navController)
                        }
                        composable(Screen.SETTINGS_SCREEN.route){
                            SettingsScreen(theme, {
                                mainActivityViewModel.changeTheme(it)
                            })
                        }
                        composable(Screen.GAME_SCREEN.route){
                            GameScreen(navController)
                        }
                    }
                }
            }
        }
    }
}

