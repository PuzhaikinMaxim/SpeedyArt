package com.mxpj.speedyart.presentation

import androidx.lifecycle.ViewModel
import com.mxpj.speedyart.domain.AppTheme
import com.mxpj.speedyart.domain.AppThemeRepository

class ThemeViewModel(private val appThemeRepository: AppThemeRepository): ViewModel() {

    fun changeTheme(isDark: Boolean) {
        val newTheme = if(isDark) AppTheme.DARK else AppTheme.LIGHT
        appThemeRepository.setAppTheme(newTheme)
    }
}