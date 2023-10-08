package com.mxpj.speedyart.domain

import androidx.lifecycle.LiveData

interface PackRepository {

    fun getPackWithProgressList(): LiveData<List<Pack>>
}