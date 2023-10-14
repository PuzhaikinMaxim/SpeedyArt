package com.mxpj.speedyart.presentation.navigation

class PictureNavParams {
    companion object {
        const val packArg = "picture"
        private const val root = "picture_screen"
        const val route = "$root/{$packArg}"

        fun buildRoute(picture: Int) = "$root/$picture"
    }
}