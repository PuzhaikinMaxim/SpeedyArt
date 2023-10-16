package com.mxpj.speedyart.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mxpj.speedyart.data.database.queryresult.TotalCompletion
import com.mxpj.speedyart.domain.model.PictureCompletion
import com.mxpj.speedyart.domain.repository.PictureCompletionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val pictureCompletionRepository: PictureCompletionRepository
): ViewModel() {

    private val _totalCompletion = MutableLiveData<TotalCompletion>()
    val totalCompletion: LiveData<TotalCompletion>
        get() = _totalCompletion

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _totalCompletion.postValue(pictureCompletionRepository.getTotalCompletion())
        }
    }
}