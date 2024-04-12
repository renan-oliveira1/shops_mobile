package com.example.myshop.domain.use_case.items

import com.example.myshop.data.repository.FakeItemRepository
import com.example.myshop.domain.model.Item
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteItemTest {
    private lateinit var deleteItemTestUseCase: DeleteItem
    private val itemRepository = FakeItemRepository()
    private val itemToAdd = Item(id = 1, name = "Item Name", quantity = 10, price = 10.00, bagId = 1)

    @Before
    fun setUp(){
        deleteItemTestUseCase = DeleteItem(itemRepository)
        runBlocking {
            itemRepository.save(itemToAdd)
        }
    }

    @Test
    fun deleteItemUseCaseTest(){
        runBlocking {
            deleteItemTestUseCase(itemToAdd)
            assert(itemRepository.items.size == 0)
        }
    }
}