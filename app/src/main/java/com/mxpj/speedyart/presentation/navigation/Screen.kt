package com.mxpj.speedyart.presentation.navigation

import com.mxpj.speedyart.presentation.PictureSelectionNavParams

enum class Screen(val route: String) {
    PACK_SELECTION_SCREEN("pack_selection_screen"),
    PICTURE_SELECTION_SCREEN(PictureSelectionNavParams.route),
    STATISTICS_SCREEN("statistics_screen"),
    PICTURE_SCREEN("picture_screen"),
    SETTINGS_SCREEN("settings_screen")
}
