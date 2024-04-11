package com.example.myshop.data.repository

import com.example.myshop.domain.model.Bag
import com.example.myshop.domain.repository.BagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeBagRepository: BagRepository {
    val bags = mutableListOf<Bag>()

    override suspend fun save(bag: Bag) {
        bags.add(bag)
    }

    override fun findAll(): Flow<List<Bag>> {
        return flow { emit(bags) }
    }

    override suspend fun findOne(id: Int): Bag? {
        return bags.find { it.id == id }
    }

    override suspend fun delete(bag: Bag) {
        bags.remove(bag)
    }
}