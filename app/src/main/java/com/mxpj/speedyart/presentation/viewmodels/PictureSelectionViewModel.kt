package com.mxpj.speedyart.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mxpj.speedyart.domain.model.Picture
import com.mxpj.speedyart.domain.repository.PictureRepository
import com.mxpj.speedyart.presentation.navigation.PictureSelectionNavParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureSelectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pictureRepository: PictureRepository
): ViewModel() {

    val pictureList =  MutableLiveData<List<Picture>>()

    val pack = savedStateHandle.get<String>(PictureSelectionNavParams.packArg).orEmpty()

    /*
    init {
        setPictureList()
    }

     */

    fun setPictureList() {
        viewModelScope.launch(Dispatchers.IO) {
            pictureList.postValue(pictureRepository.getPictureCompletionList(pack))
        }
    }
}