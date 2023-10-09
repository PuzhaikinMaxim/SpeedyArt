package com.mxpj.speedyart.domain.repository

import com.mxpj.speedyart.domain.model.AppTheme

interface AppThemeRepository {

    fun getAppTheme(): AppTheme

    fun setAppTheme(appTheme: AppTheme)
}