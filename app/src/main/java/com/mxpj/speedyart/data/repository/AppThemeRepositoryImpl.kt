package com.mxpj.speedyart.data.repository

import android.content.SharedPreferences
import com.mxpj.speedyart.domain.model.AppTheme
import com.mxpj.speedyart.domain.repository.AppThemeRepository
import javax.inject.Inject

class AppThemeRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
): AppThemeRepository {

    override fun getAppTheme(): AppTheme {
        val theme = sharedPreferences.getString(THEME_KEY, AppTheme.LIGHT.name) ?: AppTheme.LIGHT.name
        return AppTheme.valueOf(theme)
    }

    override fun setAppTheme(appTheme: AppTheme) {
        val editor = sharedPreferences.edit()
        editor.putString(THEME_KEY, appTheme.name)
        editor.apply()
    }

    companion object {
        private const val THEME_KEY = "theme"
    }
}