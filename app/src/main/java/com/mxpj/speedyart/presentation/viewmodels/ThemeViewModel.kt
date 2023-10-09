package com.mxpj.speedyart.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mxpj.speedyart.domain.model.AppTheme
import com.mxpj.speedyart.domain.repository.AppThemeRepository

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