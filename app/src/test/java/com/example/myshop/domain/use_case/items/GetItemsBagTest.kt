package com.example.myshop.domain.use_case.items

import com.example.myshop.data.repository.FakeItemRepository
import com.example.myshop.domain.model.Item
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetItemsBagTest {
    private lateinit var getItemsBag: GetItemsBag
    private val itemRepository = FakeItemRepository()

    @Before
    fun setUp(){
        getItemsBag = GetItemsBag(itemRepository)
        runBlocking {
            (1..10).forEach { number ->
                itemRepository.save(Item(id = number, name = "Item Name $number", quantity = 10, price = 10.00, bagId = number))
            }
        }
    }

    @Test
    fun getItemsBagUseCaseTest(){
        runBlocking {
            val itemFromTheBag = getItemsBag(2).first()
            assert(itemFromTheBag.isNotEmpty())
        }
    }
}