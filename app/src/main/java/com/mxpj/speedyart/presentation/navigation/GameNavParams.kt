package com.mxpj.speedyart.presentation.navigation

class GameNavParams {
    companion object {
        const val completionIdArg = "completion_id"
        private const val root = "game_screen"
        const val route = "$root/{$completionIdArg}"

        fun buildRoute(completionId: Int) = "$root/$completionId"
    }
}