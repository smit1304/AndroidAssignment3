package com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.model.movie


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey val id: Int,               // 2 digits long (11-99)
    val title: String,
    val director: String,
    val price: Double,                     // Positive value
    val releaseDate: String,               // Stored as ISO string (YYYY-MM-DD)
    val duration: Int,                     // Minutes
    val genre: String,                     // e.g., "Comedy"
    val isFavorite: Boolean = false        // On/Off
)