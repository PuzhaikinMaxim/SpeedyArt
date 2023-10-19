package com.mxpj.speedyart.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mxpj.speedyart.R
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mxpj.speedyart.domain.model.Pack
import com.mxpj.speedyart.presentation.Silver
import com.mxpj.speedyart.presentation.TopBar
import com.mxpj.speedyart.presentation.navigation.PictureSelectionNavParams
import com.mxpj.speedyart.presentation.viewmodels.PackSelectionViewModel
import com.mxpj.speedyart.ui.theme.ProgressYellow
import com.mxpj.speedyart.ui.theme.SpeedyArtTheme

//@Preview
@Composable
fun PicturePackSelectionScreen(
    navController: NavController,
    packSelectionViewModel: PackSelectionViewModel = hiltViewModel()
) {
    val lazyListState = rememberLazyListState()
    val packList by packSelectionViewModel.packList.observeAsState()
    Scaffold(
        topBar = {
            TopBar(navController)
        }
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = SpeedyArtTheme.colors.background)
        ) {
            items(packList ?: listOf()) {
                Spacer(modifier = Modifier.height(20.dp))
                PicturePackCard(pack = it, navController)
            }
        }
    }
}

@Composable
fun PicturePackCard(
    pack: Pack,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .clip(shape = RoundedCornerShape(20.dp))
            .clickable { navController.navigate(PictureSelectionNavParams.buildRoute(pack.name)) }
            .background(color = SpeedyArtTheme.colors.primary)
            .padding(10.dp),
    ) {
        Text(
            text = pack.name,
            fontFamily = FontFamily.Silver,
            fontSize = 34.sp,
            color = SpeedyArtTheme.colors.text
        )
        Box() {
            LinearProgressIndicator(
                progress = pack.completionPercent,
                modifier = Modifier
                    .height(30.dp)
                    .clip(CircleShape)
                    .fillMaxWidth(),
                color = ProgressYellow,
                backgroundColor = SpeedyArtTheme.colors.progressBarBackground
            )
            Text(
                text = "${((pack.completionPercent*100)).toInt()}%",
                fontFamily = FontFamily.Silver,
                fontSize = 32.sp,
                color = SpeedyArtTheme.colors.text,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 5.dp)
            )
        }
        Row() {
            Text(
                text = stringResource(R.string.size, pack.size.first, pack.size.second),
                fontFamily = FontFamily.Silver,
                fontSize = 28.sp,
                color = SpeedyArtTheme.colors.text
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(R.string.pack_pictures_amount, pack.picturesAmount),
                fontFamily = FontFamily.Silver,
                fontSize = 28.sp,
                color = SpeedyArtTheme.colors.text
            )
        }
    }
}