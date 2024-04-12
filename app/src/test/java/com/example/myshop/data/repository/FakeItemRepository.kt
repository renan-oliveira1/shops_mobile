package com.example.myshop.data.repository

import com.example.myshop.domain.model.Item
import com.example.myshop.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeItemRepository: ItemRepository {
    val items = mutableListOf<Item>()
    override fun findItemsBag(idBag: Int): Flow<List<Item>> {
        return flow{ emit(items) }
    }

    override suspend fun save(item: Item) {
        items.add(item)
    }

    override fun findAll(): Flow<List<Item>> {
        TODO("Not yet implemented")

    }

    override suspend fun findOne(id: Int): Item? {
        return items.find { it.id == id }
    }

    override suspend fun delete(item: Item) {
        items.remove(item)
    }

}