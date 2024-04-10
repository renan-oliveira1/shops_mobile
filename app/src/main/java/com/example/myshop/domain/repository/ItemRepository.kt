package com.example.myshop.domain.repository

import com.example.myshop.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository: IRepository<Item, Int>{
    fun findItemsBag(idBag: Int): Flow<List<Item>>
}