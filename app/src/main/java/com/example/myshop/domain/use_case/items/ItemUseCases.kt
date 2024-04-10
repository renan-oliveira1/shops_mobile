package com.example.myshop.domain.use_case.items

data class ItemUseCases(
    val getItemsBag: GetItemsBag,
    val deleteItem: DeleteItem,
    val addItem: AddItem,
    val getItem: GetItem
) {
}