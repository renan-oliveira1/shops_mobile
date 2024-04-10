package com.example.myshop.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myshop.domain.model.Bag
import com.example.myshop.domain.model.Item

@Database(
    entities = [Bag::class, Item::class],
    version = 1
)
abstract class ApplicationDatabase: RoomDatabase() {
    abstract val itemDao: ItemDao

    // abstract val itemDao: ItemDao
    abstract val bagDao: BagDao

    companion object{
        const val DATABASE_NAME = "myshop_db"
    }
}