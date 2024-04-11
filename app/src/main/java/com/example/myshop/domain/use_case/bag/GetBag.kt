package com.example.myshop.domain.use_case.bag

import com.example.myshop.domain.model.Bag
import com.example.myshop.domain.repository.BagRepository

class GetBag(
    private val bagRepository: BagRepository
) {
    suspend operator fun invoke(id: Int): Bag?{
        return bagRepository.findOne(id)
    }
}