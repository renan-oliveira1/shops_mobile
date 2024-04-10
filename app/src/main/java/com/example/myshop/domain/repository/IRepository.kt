package com.example.myshop.domain.repository

import kotlinx.coroutines.flow.Flow

interface IRepository<T, K> {
    suspend fun save(t: T)
    fun findAll(): Flow<List<T>>
    suspend fun findOne(id: Int): T
    suspend fun delete(t: T)
}