package com.mxpj.speedyart.presentation.viewmodels

import com.mxpj.speedyart.domain.repository.AppThemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appThemeRepository: AppThemeRepository
): ThemeViewModel(appThemeRepository)