package com.example.myshop.domain.use_case.util

sealed class ItemOrder(val orderType: OrderType) {
    class Name(orderType: OrderType): ItemOrder(orderType)
    class Price(orderType: OrderType): ItemOrder(orderType)
}