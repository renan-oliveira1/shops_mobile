package com.example.myshop.domain.use_case.bag

import com.example.myshop.data.repository.FakeBagRepository
import com.example.myshop.domain.model.Bag
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class GetBagTest{
    private lateinit var getBagUseCases: GetBag
    private val bagRepository = FakeBagRepository()

    @Before
    fun setUp(){
        getBagUseCases = GetBag(bagRepository)
        runBlocking {
            (1..10).forEach { number ->
                bagRepository.save(Bag(id = number, title = "Bag number $number", timestamp = System.currentTimeMillis()))
            }
        }
    }

    @Test
    fun getBagUseCaseTest(){
        runBlocking {
            val bag = getBagUseCases(2)
            assert(bag != null)
            assertEquals(bag?.id, 2)
            assertEquals(bag?.title, "Bag number 2")
        }
    }

    @Test
    fun getBagNotExistsUseCaseTest(){
        runBlocking {
            val bag = getBagUseCases(11)
            assert(bag == null)
        }
    }
}