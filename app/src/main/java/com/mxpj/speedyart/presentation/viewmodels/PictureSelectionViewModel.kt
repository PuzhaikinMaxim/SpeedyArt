package com.mxpj.speedyart.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mxpj.speedyart.domain.model.PictureCompletion
import com.mxpj.speedyart.domain.repository.PictureRepository
import com.mxpj.speedyart.presentation.PictureSelectionNavParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureSelectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pictureRepository: PictureRepository
): ViewModel() {

    private val pictureList =  MutableLiveData<List<PictureCompletion>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val pack = savedStateHandle.get<String>(PictureSelectionNavParams.packArg).orEmpty()
            pictureList.postValue(pictureRepository.getPictureCompletionList(pack))
        }
    }
}