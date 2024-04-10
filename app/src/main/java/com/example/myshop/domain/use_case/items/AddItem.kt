package com.example.myshop.domain.use_case.items

import com.example.myshop.data.repository.ItemRepositoryImpl
import com.example.myshop.domain.model.Item
import com.example.myshop.domain.repository.ItemRepository

class AddItem(
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(item: Item){
        try {
            itemRepository.save(item)
        }catch (e: Exception){
            var i = 0
        }
    }
}