package com.mxpj.speedyart.presentation.viewmodels

import androidx.lifecycle.*
import com.mxpj.speedyart.domain.model.PictureCompletion
import com.mxpj.speedyart.domain.repository.PictureRepository
import com.mxpj.speedyart.presentation.navigation.PictureNavParams
import kotlinx.coroutines.launch
import javax.inject.Inject

class PictureViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pictureRepository: PictureRepository
): ViewModel() {

    private val _pictureCompletion = MutableLiveData<PictureCompletion>()
    val pictureCompletion: LiveData<PictureCompletion>
        get() = _pictureCompletion

    init {
        viewModelScope.launch {
            val picture =
                savedStateHandle.get<Int>(PictureNavParams.packArg)
                    ?: throw RuntimeException("Picture nav arg is null")
            _pictureCompletion.postValue(pictureRepository.getPictureCompletion(picture))
        }
    }
}