package com.example.odyssey.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "destinations")
data class DestinationEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "country")
    val country: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,

    @ColumnInfo(name = "rating")
    val rating: Float,

    @ColumnInfo(name = "price")
    val price: Double,

    @ColumnInfo(name = "currency")
    val currency: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "isPopular")
    val isPopular: Boolean = false,

    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean = false
)
