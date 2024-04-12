package com.example.myshop.domain.use_case.items

import com.example.myshop.data.repository.FakeItemRepository
import com.example.myshop.domain.model.Item
import org.junit.jupiter.api.Assertions.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetItemTest {
    private lateinit var getItemUseCase: GetItem
    private val itemRepository = FakeItemRepository()

    @Before
    fun setUp(){
        getItemUseCase = GetItem(itemRepository)
        runBlocking {
            (1..10).forEach { number ->
                itemRepository.save(Item(id = number, name = "Item Name $number", quantity = 10, price = 10.00, bagId = number))
            }
        }
    }

    @Test
    fun getItemUseCaseTest(){
        runBlocking {
            val item = getItemUseCase(4)
            assert(item != null)
            assertEquals(item?.id, 4)
            assertEquals(item?.name, "Item Name 4")

        }
    }
}