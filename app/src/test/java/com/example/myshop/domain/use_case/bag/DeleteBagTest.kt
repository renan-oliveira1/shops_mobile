package com.example.myshop.domain.use_case.bag

import com.example.myshop.data.repository.FakeBagRepository
import com.example.myshop.domain.model.Bag
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class DeleteBagTest{
    private lateinit var deleteBagTest: DeleteBag
    private val bagRepository = FakeBagRepository()
    private val timestamp = System.currentTimeMillis()

    @Before
    fun setUp(){
        deleteBagTest = DeleteBag(bagRepository)
        runBlocking {
            (1..10).forEach { number ->
                bagRepository.save(Bag(id = number, title = "Bag number $number", timestamp = timestamp))
            }
        }
    }

    @Test
    fun deleteBagUseCase() {
        runBlocking {
            assert(bagRepository.bags.size == 10)
            val bagNumberTwo = Bag(id = 1, title = "Bag number 1", timestamp = timestamp)
            deleteBagTest(bagNumberTwo)
            assert(bagRepository.bags.size == 9)
            assert(bagRepository.bags.filter { it.id == 1 }.isEmpty())
        }
    }
}