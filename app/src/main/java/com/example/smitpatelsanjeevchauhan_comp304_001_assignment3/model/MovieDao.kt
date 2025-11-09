package com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.model


import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAll(): Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("SELECT * FROM movies WHERE isFavorite = 1")
    fun getFavorites(): Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSampleMovies(movies: List<Movie>)
}