package com.example.myshop.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(entity = Bag::class, parentColumns = ["id"], childColumns = ["bagId"], onDelete = ForeignKey.CASCADE)]
)
data class Item(
    @PrimaryKey val id: Int? = null,
    val name: String,
    val quantity: Int,
    val price: Double,
    val bagId: Int
)

class InvalidItemException(message: String): Exception(message)
