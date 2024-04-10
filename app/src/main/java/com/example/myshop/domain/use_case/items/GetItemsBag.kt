package com.example.myshop.domain.use_case.items

import com.example.myshop.domain.model.Item
import com.example.myshop.domain.repository.ItemRepository
import com.example.myshop.domain.use_case.util.BagOrder
import com.example.myshop.domain.use_case.util.ItemOrder
import com.example.myshop.domain.use_case.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetItemsBag(private val itemRepository: ItemRepository) {
    operator fun invoke(idBag: Int, itemOrder: ItemOrder = ItemOrder.Name(OrderType.Descending)): Flow<List<Item>>{
        return  itemRepository.findItemsBag(idBag).map {
            item ->
                when(itemOrder.orderType){
                    is OrderType.Ascending -> {
                        when(itemOrder){
                            is ItemOrder.Name -> item.sortedBy { it.name }
                            is ItemOrder.Price -> item.sortedBy { it.price }
                        }
                    }
                    is OrderType.Descending -> {
                        when(itemOrder){
                            is ItemOrder.Name -> item.sortedByDescending { it.name }
                            is ItemOrder.Price -> item.sortedByDescending { it.price }
                        }
                    }
                }
        }
    }
}