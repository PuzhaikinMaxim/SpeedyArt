package com.mxpj.speedyart.presentation

import androidx.lifecycle.ViewModel
import com.mxpj.speedyart.domain.AppThemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appThemeRepository: AppThemeRepository
): ThemeViewModel(appThemeRepository) {

}