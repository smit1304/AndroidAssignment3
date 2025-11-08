package com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAll(): Flow<List<Product>>    // Retrieves all products as a Flow

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product) // Inserts a product, replacing if conflict occurs

    @Update
    suspend fun update(product: Product) // Updates an existing product

    @Delete
    suspend fun delete(product: Product) // Deletes a product

    @Query("SELECT * FROM products WHERE isFavorite = 1")
    fun getFavorites(): Flow<List<Product>> // Retrieves favorite products as a Flow

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSampleProducts(products: List<Product>) // Inserts sample data, ignoring conflicts
}