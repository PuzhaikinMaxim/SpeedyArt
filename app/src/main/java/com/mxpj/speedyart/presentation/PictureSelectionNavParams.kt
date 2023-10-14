package com.mxpj.speedyart.presentation

class PictureSelectionNavParams {
    companion object {
        const val packArg = "pack"
        const val root = "picture_selection_screen"
        const val route = "$root/{$packArg}"

        fun buildPictureSelectionScreenRoute(pack: String) = "$root/$pack"
    }
}
