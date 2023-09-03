package com.mxpj.speedyart.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mxpj.speedyart.R

@Preview
@Composable
fun TopBar() {
    TopAppBar(
        backgroundColor = Color.Gray,
        contentColor = Color.Black
    ) {
        TopBarImage(R.drawable.ic_trophy) {}
        Spacer(modifier = Modifier.weight(1f))
        TopBarImage(R.drawable.ic_settings_alt) {}
    }
}

@Composable
fun TopBarImage(resource: Int, onClickListener: () -> (Unit)) {
    IconButton(
        onClick = {
            onClickListener()
        }
    ) {
        Image(
            bitmap = PixelImageProvider.getPixelImageBitmap(resource),
            contentDescription = "",
            filterQuality = FilterQuality.None,
            modifier = Modifier
                .height(35.dp)
                .aspectRatio(1f)
        )
    }
}