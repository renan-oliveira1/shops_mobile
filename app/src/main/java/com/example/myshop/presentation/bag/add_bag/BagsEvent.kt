package com.example.myshop.presentation.bag.add_bag

import com.example.myshop.domain.model.Bag
import com.example.myshop.domain.use_case.util.BagOrder

sealed class BagsEvent {
    data class Order(val bagOrder: BagOrder): BagsEvent()
    data class SaveBag(val title: String): BagsEvent()
    data class DeleteShop(val bag: Bag): BagsEvent()
    object RestoreShop: BagsEvent()

}