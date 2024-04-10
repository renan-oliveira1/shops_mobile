package com.example.myshop.data.repository

import com.example.myshop.data.data_source.BagDao
import com.example.myshop.domain.model.Bag
import com.example.myshop.domain.repository.BagRepository
import kotlinx.coroutines.flow.Flow

class BagRepositoryImpl(
    private val bagDao: BagDao
): BagRepository{
    override suspend fun save(bag: Bag) {
        return bagDao.save(bag)
    }

    override fun findAll(): Flow<List<Bag>> {
        return bagDao.findAll()
    }

    override suspend fun findOne(id: Int): Bag {
        return bagDao.findOne(id)
    }

    override suspend fun delete(bag: Bag) {
        return bagDao.delete(bag)
    }

}