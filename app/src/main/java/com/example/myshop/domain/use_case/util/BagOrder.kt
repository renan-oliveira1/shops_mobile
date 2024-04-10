package com.example.myshop.domain.use_case.util

sealed class BagOrder(val orderType: OrderType) {
    class Title(orderType: OrderType): BagOrder(orderType)
    class Date(orderType: OrderType): BagOrder(orderType)
}