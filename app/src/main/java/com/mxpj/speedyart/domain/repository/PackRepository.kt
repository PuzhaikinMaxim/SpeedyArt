package com.mxpj.speedyart.domain.repository

import androidx.lifecycle.LiveData
import com.mxpj.speedyart.domain.model.Pack

interface PackRepository {

    fun getPackWithProgressList(): LiveData<List<Pack>>
}