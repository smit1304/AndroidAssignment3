package com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.model.product

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// Room database containing the products table
@Database(entities = [Product::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao // Provides access to ProductDao

    companion object {
        @Volatile
        private var Instance: ProductDatabase? = null

        // Singleton pattern to get or create the database instance
        fun getDatabase(context: Context): ProductDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "product_db"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Prepopulate database with sample model on first creation
                            CoroutineScope(Dispatchers.IO).launch {
                                getDatabase(context).productDao().insertSampleProducts(
                                    listOf(
                                        Product(
                                            101,
                                            "Phone",
                                            299.99,
                                            1,
                                            "2025-04-01",
                                            "Electronics",
                                            false
                                        ),
                                        Product(
                                            102,
                                            "Laptop",
                                            999.99,
                                            2,
                                            "2025-04-02",
                                            "Electronics",
                                            true
                                        ),
                                        Product(
                                            103,
                                            "Microwave",
                                            149.99,
                                            3,
                                            "2025-04-03",
                                            "Appliances",
                                            false
                                        )
                                    )
                                )
                            }
                        }
                    })
                    .build()
                    .also { Instance = it }
            }
        }
    }
}