package com.mxpj.speedyart.domain

interface AppThemeRepository {

    fun getAppTheme(): AppTheme

    fun setAppTheme(appTheme: AppTheme)
}