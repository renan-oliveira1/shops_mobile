package com.example.myshop.domain.use_case.bag

import com.example.myshop.domain.model.Bag
import com.example.myshop.domain.repository.BagRepository
import com.example.myshop.domain.use_case.util.OrderType
import com.example.myshop.domain.use_case.util.BagOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetBags(
    private val bagRepository: BagRepository
) {

    operator fun invoke(bagOrder: BagOrder = BagOrder.Date(OrderType.Descending)): Flow<List<Bag>> {
        return bagRepository.findAll()
            .map { shopItem  ->
                when(bagOrder.orderType){
                    is OrderType.Ascending -> {
                        when(bagOrder){
                            is BagOrder.Title -> shopItem.sortedBy { it.title }
                            is BagOrder.Date -> shopItem.sortedBy { it.timestamp }
                        }
                    }
                    is OrderType.Descending -> {
                        when(bagOrder){
                            is BagOrder.Title -> shopItem.sortedByDescending { it.title }
                            is BagOrder.Date -> shopItem.sortedByDescending { it.timestamp }
                        }
                    }
                }
            }
    }
}