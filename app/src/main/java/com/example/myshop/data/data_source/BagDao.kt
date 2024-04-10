package com.example.myshop.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myshop.domain.model.Bag
import kotlinx.coroutines.flow.Flow

@Dao
interface BagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(bag: Bag)

    @Query("SELECT * FROM bag")
    fun findAll(): Flow<List<Bag>>

    @Query("SELECT * FROM bag WHERE id = :id")
    suspend fun findOne(id: Int): Bag

    @Delete
    suspend fun delete(bag: Bag)
}