package com.mxpj.speedyart.presentation.game

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.drawable.toBitmap
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
                    Image(
                        bitmap.asImageBitmap(),
                        "test",
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxWidth(),
                        filterQuality = FilterQuality.None
                    )
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpeedyArtTheme {
        Greeting("Android")
    }
}