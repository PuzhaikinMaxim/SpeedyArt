package com.mxpj.speedyart.presentation.viewmodels

import androidx.lifecycle.*
import com.mxpj.speedyart.domain.model.PictureWithStatistics
import com.mxpj.speedyart.domain.repository.PictureRepository
import com.mxpj.speedyart.presentation.BitmapToPictureClassParser
import com.mxpj.speedyart.presentation.PixelImageProvider
import com.mxpj.speedyart.presentation.navigation.PictureNavParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pictureRepository: PictureRepository,
    private val bitmapToPictureClassParser: BitmapToPictureClassParser
): ViewModel() {

    private val _pictureCompletion = MutableLiveData<PictureWithStatistics>()
    val pictureCompletion: LiveData<PictureWithStatistics>
        get() = _pictureCompletion

    init {
        viewModelScope.launch {
            val picture =
                savedStateHandle.get<String>(PictureNavParams.packArg)?.toInt()
                    ?: throw RuntimeException("Picture nav arg is null")
            val pictureStatisticsTemp = pictureRepository.getPictureStatistics(picture)
            val parcedPicture = bitmapToPictureClassParser.parseToPicture(
                PixelImageProvider.getPixelBitmap(pictureStatisticsTemp.picture.pictureAsset)
            )
            val pictureStatistics = pictureStatisticsTemp.copy(
                amountOfCells = parcedPicture.unfilledCells.size,
                size = Pair(parcedPicture.gridCells[0].size, parcedPicture.gridCells.size)
            )
            _pictureCompletion.postValue(pictureStatistics)
        }
    }
}