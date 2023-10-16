package com.mxpj.speedyart.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mxpj.speedyart.domain.repository.PictureCompletionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val pictureCompletionRepository: PictureCompletionRepository
): ViewModel() {

    private val _progressReset = MutableLiveData<Unit>()
    val progressReset: LiveData<Unit>
        get() = _progressReset

    fun resetProgress() {
        viewModelScope.launch(Dispatchers.IO) {
            pictureCompletionRepository.resetProgress()
            _progressReset.postValue(Unit)
        }
    }
}