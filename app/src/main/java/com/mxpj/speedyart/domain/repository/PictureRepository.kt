package com.mxpj.speedyart.domain.repository

import androidx.lifecycle.LiveData
import com.mxpj.speedyart.domain.model.Picture
import com.mxpj.speedyart.domain.model.PictureCompletion

interface PictureRepository {

    fun getPictureCompletionList(): LiveData<List<PictureCompletion>>
}