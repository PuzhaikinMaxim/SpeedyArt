package com.mxpj.speedyart.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.mxpj.speedyart.domain.PackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PackViewModel @Inject constructor(
    private val packRepository: PackRepository
): ViewModel() {

    private val packList = packRepository.getPackWithProgressList()
}