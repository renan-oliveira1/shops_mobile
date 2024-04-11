package com.example.myshop.domain.use_case.bag

import com.example.myshop.data.repository.FakeBagRepository
import com.example.myshop.domain.model.Bag
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

// TODO: When order implemented, implement tests
class GetBagsTest{

    private lateinit var getBagsUseCase: GetBags
    private val bagRepository = FakeBagRepository()

    @Before
    fun setUp(){
        getBagsUseCase = GetBags(bagRepository)
        runBlocking {
            (1..10).forEach { number ->
                bagRepository.save(Bag(id = number, title = "Bag number $number", timestamp = System.currentTimeMillis()))
            }
        }
    }

    @Test
    fun getBagsUseCaseTest(){
        runBlocking {
            val bags = getBagsUseCase().first()
            assertEquals(bags.size, 10)
        }
    }
}