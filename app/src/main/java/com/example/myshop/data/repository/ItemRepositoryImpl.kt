package com.example.myshop.data.repository

import com.example.myshop.data.data_source.ItemDao
import com.example.myshop.domain.model.Item
import com.example.myshop.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow

class ItemRepositoryImpl(
    private val dao: ItemDao
): ItemRepository{
    override fun findItemsBag(idBag: Int): Flow<List<Item>> {
        return dao.findAllByBag(idBag)
    }

    override suspend fun save(item: Item) {
        return dao.save(item)
    }

    override fun findAll(): Flow<List<Item>> {
        TODO("Not yet implemented")
    }

    override suspend fun findOne(id: Int): Item {
        return dao.findOne(id)
    }

    override suspend fun delete(item: Item) {
        dao.delete(item)
    }

}