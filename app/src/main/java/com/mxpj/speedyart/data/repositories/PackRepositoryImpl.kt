package com.mxpj.speedyart.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mxpj.speedyart.data.database.dao.PackDao
import com.mxpj.speedyart.data.mappers.PackMapper
import com.mxpj.speedyart.domain.Pack
import com.mxpj.speedyart.domain.PackRepository
import javax.inject.Inject

class PackRepositoryImpl @Inject constructor(
    private val packDao: PackDao,
    private val packMapper: PackMapper
): PackRepository {

    private val packWithProgressList= packDao.getPackListWithProgress()

    override fun getPackWithProgressList(): LiveData<List<Pack>> {
        return Transformations.map(packWithProgressList){
            packMapper.mapPackWithProgressListToPack(it)
        }
    }
}