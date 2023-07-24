package com.mxpj.speedyart.presentation.game

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mxpj.speedyart.domain.Picture

class GameViewModel: ViewModel() {

    private val _picture = MutableLiveData<Picture>()
    val picture: LiveData<Picture>
        get() = _picture

    private val _mistakesAmount = MutableLiveData<Int>(0)
    val mistakesAmount: LiveData<Int>
        get() = _mistakesAmount

    init {

    }

    fun setPicture(newPicture: Picture){
        _picture.value = newPicture
    }

    fun getColor(): Int {
        return picture.value!!.availablePalette[0]
    }

    fun onClick(color: Int, cellPosition: Pair<Int, Int>) {
        val cell = picture.value!!.getCell(cellPosition.first, cellPosition.second)
        if(color == cell.rightColor){
            cell.currentColor = color
            picture.value!!.unfilledCells.remove(cellPosition)
            _picture.value = _picture.value
        }
        else{
            _mistakesAmount.value = _mistakesAmount.value!!.plus(1)
        }
    }
}