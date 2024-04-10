package com.example.myshop.domain.use_case.items

import com.example.myshop.domain.model.Item
import com.example.myshop.domain.repository.ItemRepository

class DeleteItem(
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(item: Item){
        itemRepository.delete(item)
    }
}