package com.example.myshop.domain.use_case.bag

import com.example.myshop.data.repository.FakeBagRepository
import com.example.myshop.domain.model.Bag
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class AddBagTest(){
    private lateinit var addBagUseCase: AddBag
    private val nameBag = "Name of the Bag!!"
    private val bagRepository = FakeBagRepository()

    @Before
    fun setUp(){
        addBagUseCase = AddBag(bagRepository)
        runBlocking {
            addBagUseCase(nameBag)
        }
    }

    @Test
    fun addBagTest(){
        assert(bagRepository.bags.size == 1)
        assertEquals(nameBag, bagRepository.bags[0].title)
    }

}