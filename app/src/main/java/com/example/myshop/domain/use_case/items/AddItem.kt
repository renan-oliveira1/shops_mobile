package com.example.myshop.domain.use_case.items

import com.example.myshop.data.repository.ItemRepositoryImpl
import com.example.myshop.domain.model.Bag
import com.example.myshop.domain.model.InvalidItemException
import com.example.myshop.domain.model.Item
import com.example.myshop.domain.repository.BagRepository
import com.example.myshop.domain.repository.ItemRepository

class AddItem(
    private val itemRepository: ItemRepository,
    private val bagRepository: BagRepository
) {
    suspend operator fun invoke(item: Item){

        if(bagRepository.findOne(item.bagId) == null){
            throw InvalidItemException("Bag of item doesn't exists!!")
        }

        itemRepository.save(item)
    }
}