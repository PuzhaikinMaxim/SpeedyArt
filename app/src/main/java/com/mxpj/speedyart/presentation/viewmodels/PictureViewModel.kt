package com.mxpj.speedyart.presentation.viewmodels

import androidx.lifecycle.*
import com.mxpj.speedyart.domain.model.PictureCompletion
import com.mxpj.speedyart.domain.model.PictureStatistics
import com.mxpj.speedyart.domain.repository.PictureRepository
import com.mxpj.speedyart.presentation.ImageToPictureClassParser
import com.mxpj.speedyart.presentation.PixelImageProvider
import com.mxpj.speedyart.presentation.navigation.PictureNavParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pictureRepository: PictureRepository,
    private val imageToPictureClassParser: ImageToPictureClassParser
): ViewModel() {

    private val _pictureCompletion = MutableLiveData<PictureStatistics>()
    val pictureCompletion: LiveData<PictureStatistics>
        get() = _pictureCompletion

    init {
        viewModelScope.launch {
            val picture =
                savedStateHandle.get<String>(PictureNavParams.packArg)?.toInt()
                    ?: throw RuntimeException("Picture nav arg is null")
            val pictureStatisticsTemp = pictureRepository.getPictureStatistics(picture)
            val parcedPicture = imageToPictureClassParser.parseToPicture(
                PixelImageProvider.getPixelBitmap(pictureStatisticsTemp.pictureCompletion.pictureAsset)
            )
            val pictureStatistics = pictureStatisticsTemp.copy(
                amountOfCells = parcedPicture.unfilledCells.size,
                size = Pair(parcedPicture.gridCells[0].size, parcedPicture.gridCells.size)
            )
            _pictureCompletion.postValue(pictureStatistics)
        }
    }
}