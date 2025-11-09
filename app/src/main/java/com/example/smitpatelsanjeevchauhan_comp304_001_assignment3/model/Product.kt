package com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Represents a product entity in the Room database
@Entity(tableName = "products")
data class Product(
    @PrimaryKey val id: Int,
    val name: String,
    val price: Double,
    val deliveryDate: String,
    val category: String,
    val isFavorite: Boolean
)