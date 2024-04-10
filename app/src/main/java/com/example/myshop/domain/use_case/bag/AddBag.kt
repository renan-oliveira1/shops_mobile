package com.example.myshop.domain.use_case.bag

import com.example.myshop.domain.model.Bag
import com.example.myshop.domain.repository.BagRepository

class AddBag(
    private val bagRepository: BagRepository
) {
    suspend operator fun invoke(bagTitle: String){
        val bag = Bag(title = bagTitle, timestamp = System.currentTimeMillis())
        return bagRepository.save(bag)
    }
}