package com.example.myshop.domain.use_case.items

import com.example.myshop.data.repository.FakeItemRepository
import com.example.myshop.domain.model.Item
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class AddItemTest{
    private lateinit var addItemUseCase: AddItem
    private val itemRepository = FakeItemRepository()

    @Before
    fun setUp(){
        addItemUseCase = AddItem(itemRepository)
    }

    @Test
    fun addItemTestUseCase(){
        runBlocking {
            val item = Item(id = 1, name = "Item Name", quantity = 10, price = 10.00, bagId = 1)
            itemRepository.save(item)
            assert(itemRepository.items.size == 1)
            assertEquals(item, itemRepository.items[0])
        }
    }
}