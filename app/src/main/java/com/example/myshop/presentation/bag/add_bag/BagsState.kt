package com.example.myshop.presentation.bag.add_bag

import com.example.myshop.domain.model.Bag
import com.example.myshop.domain.use_case.util.OrderType
import com.example.myshop.domain.use_case.util.BagOrder

data class BagsState(
    val shops: List<Bag> = emptyList(),
    val bagOrder: BagOrder = BagOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
