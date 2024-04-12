package com.example.myshop.presentation.bag.bag_items

import com.example.myshop.domain.model.Bag
import com.example.myshop.domain.model.Item
import com.example.myshop.domain.use_case.util.ItemOrder
import com.example.myshop.presentation.bag.add_bag.BagsEvent

sealed class ItemEvent {
    data class Order(val itemOrder: ItemOrder): ItemEvent()
    data class SaveItem(val name: String, val price: String, val quantity: String, val idItem: Int?): ItemEvent()
    data class DeleteShop(val item: Item): ItemEvent()
    object RestoreShop: ItemEvent()
}