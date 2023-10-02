package com.mxpj.speedyart.presentation

import android.content.res.Resources.Theme
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mxpj.speedyart.domain.AppTheme
import com.mxpj.speedyart.domain.AppThemeRepository

abstract class ThemeViewModel(private val appThemeRepository: AppThemeRepository): ViewModel() {

    private val _theme = MutableLiveData<AppTheme>()
    val theme: LiveData<AppTheme>
        get() = _theme

    init {
        _theme.value = appThemeRepository.getAppTheme()
    }

    fun changeTheme(isDark: Boolean) {
        val newTheme = if(isDark) AppTheme.DARK else AppTheme.LIGHT
        appThemeRepository.setAppTheme(newTheme)
        _theme.value = newTheme
    }
}