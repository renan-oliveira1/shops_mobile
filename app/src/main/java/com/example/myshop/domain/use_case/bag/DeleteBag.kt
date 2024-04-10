package com.example.myshop.domain.use_case.bag

import com.example.myshop.domain.model.Bag
import com.example.myshop.domain.repository.BagRepository

class DeleteBag(
    private val bagRepository: BagRepository
) {
    suspend operator fun invoke(bag: Bag){
        bagRepository.delete(bag)
    }
}