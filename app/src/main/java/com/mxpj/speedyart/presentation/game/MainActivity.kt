package com.mxpj.speedyart.presentation.game

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mxpj.speedyart.R
import com.mxpj.speedyart.data.database.DatabaseDataInserter
import com.mxpj.speedyart.domain.model.AppTheme
import com.mxpj.speedyart.domain.model.GamePicture
import com.mxpj.speedyart.presentation.ImageToPictureClassParser
import com.mxpj.speedyart.presentation.navigation.Screen
import com.mxpj.speedyart.presentation.screens.*
import com.mxpj.speedyart.presentation.viewmodels.MainViewModel
import com.mxpj.speedyart.ui.theme.SpeedyArtTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var gamePicture: GamePicture

    private val mainActivityViewModel: MainViewModel by viewModels()

    private var backPressedTime = 0L

    private val routeOnBackCallbackMap = mapOf(
        Screen.GAME_SCREEN.route to { onGameScreenBackPressed() }
    )

    private var currentRoute = ""

    @Inject
    lateinit var databaseDataInserter: DatabaseDataInserter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val parser = ImageToPictureClassParser()
        gamePicture = parser.parseToPicture(BitmapFactory.decodeResource(
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
                    val currentBackStackEntry by navController.currentBackStackEntryAsState()
                    currentRoute = currentBackStackEntry?.destination?.route ?: ""
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
                            GameScreen(navController) {
                                super.onBackPressed()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun onGameScreenBackPressed() {
        if(backPressedTime + DELAY_BETWEEN_PRESSES > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            Toast.makeText(
                this,
                getString(R.string.on_back_pressed_toast),
                Toast.LENGTH_SHORT
            ).show()
            backPressedTime = System.currentTimeMillis()
        }
    }
    override fun onBackPressed() {
        routeOnBackCallbackMap.getOrDefault(currentRoute) { super.onBackPressed() }.invoke()
    }

    companion object {
        private const val DELAY_BETWEEN_PRESSES = 1000L
    }
}

