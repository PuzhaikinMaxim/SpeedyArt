package com.mxpj.speedyart.presentation.navigation

class PictureSelectionNavParams {
    companion object {
        const val packArg = "pack"
        private const val root = "picture_selection_screen"
        const val route = "$root/{$packArg}"

        fun buildRoute(pack: String) = "$root/$pack"
    }
}
