package com.mxpj.speedyart.presentation.navigation

enum class Screen(val route: String) {
    PACK_SELECTION_SCREEN("pack_selection_screen"),
    PICTURE_SELECTION_SCREEN(PictureSelectionNavParams.route),
    STATISTICS_SCREEN("statistics_screen"),
    PICTURE_SCREEN(PictureNavParams.route),
    SETTINGS_SCREEN("settings_screen"),
    GAME_SCREEN("game_screen")
}
