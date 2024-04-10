package com.example.myshop.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myshop.domain.model.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(item: Item)

    @Query("SELECT * FROM item WHERE bagId = :id")
    fun findAllByBag(id: Int): Flow<List<Item>>

    @Query("SELECT * FROM item WHERE id = :id")
    suspend fun findOne(id: Int): Item

    @Delete
    suspend fun delete(item: Item)
}