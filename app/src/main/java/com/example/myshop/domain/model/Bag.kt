package com.example.myshop.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bag(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val timestamp: Long
)
