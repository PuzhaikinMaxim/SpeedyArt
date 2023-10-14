package com.mxpj.speedyart.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.mxpj.speedyart.domain.repository.PackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PackSelectionViewModel @Inject constructor(
    private val packRepository: PackRepository
): ViewModel() {

    private val packList = packRepository.getPackWithProgressList()
}