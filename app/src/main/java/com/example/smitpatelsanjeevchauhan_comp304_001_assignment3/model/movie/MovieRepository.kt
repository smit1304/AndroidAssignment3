package com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.model.movie
import kotlinx.coroutines.flow.Flow


class MovieRepository(private val dao: MovieDao) {
    val movies: Flow<List<Movie>> = dao.getAll()
    val favoriteMovies: Flow<List<Movie>> = dao.getFavorites()

    suspend fun addMovie(movie: Movie) = dao.insert(movie)
    suspend fun updateMovie(movie: Movie) = dao.update(movie)
    suspend fun deleteMovie(movie: Movie) = dao.delete(movie)
    suspend fun insertSampleMovies(movies: List<Movie>) = dao.insertSampleMovies(movies)
}