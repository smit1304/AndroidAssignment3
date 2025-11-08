package com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.data.Movie
import com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.data.MovieDatabase
import com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.data.MovieRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class MovieViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: MovieRepository

    val allMovies: LiveData<List<Movie>>
    val favoriteMovies: LiveData<List<Movie>>

    private val _addMovieSuccess = MutableStateFlow(false)
    val addMovieSuccess: StateFlow<Boolean> = _addMovieSuccess.asStateFlow()

    // Form state for adding a movie
    data class AddMovieState(
        val id: String = "",
        val title: String = "",
        val director: String = "",
        val price: String = "",
        val releaseDate: String = "",
        val duration: String = "",
        val genre: String = "",
        val isFavorite: Boolean = false,
        val errors: List<String> = emptyList()
    )

    private val _addMovieState = MutableStateFlow(AddMovieState())
    val addMovieState: StateFlow<AddMovieState> = _addMovieState.asStateFlow()

    init {
        val dao = MovieDatabase.getDatabase(application).movieDao()
        repository = MovieRepository(dao)
        allMovies = repository.movies.asLiveData()
        favoriteMovies = repository.favoriteMovies.asLiveData()
    }

    fun validateAndAddMovie() {
        val state = _addMovieState.value
        val errors = mutableListOf<String>()

        // ID validation (2 digits, 11-99)
        val id = state.id.toIntOrNull()
        if (id == null || id !in 11..99) errors.add("Invalid ID (11-99)")

        // Title validation
        if (state.title.isBlank()) errors.add("Title required")

        // Director validation
        if (state.director.isBlank()) errors.add("Director name required")

        // Price validation
        val price = state.price.toDoubleOrNull()
        if (price == null || price <= 0) errors.add("Price must be positive")

        // Release date validation
        val releaseDate = try {
            LocalDate.parse(state.releaseDate)
        } catch (e: Exception) { null }
        if (releaseDate == null) errors.add("Release date invalid (use YYYY-MM-DD)")

        // Duration validation
        val duration = state.duration.toIntOrNull()
        if (duration == null || duration <= 0) errors.add("Duration must be a positive integer")

        // Genre validation
        val validGenres = listOf("Family", "Comedy", "Thriller", "Action")
        if (state.genre !in validGenres) errors.add("Select a valid genre")

        if (errors.isEmpty()) {
            insert(
                Movie(
                    id = id!!,
                    title = state.title,
                    director = state.director,
                    price = price!!,
                    releaseDate = state.releaseDate,
                    duration = duration!!,
                    genre = state.genre,
                    isFavorite = state.isFavorite
                )
            )
            _addMovieState.update { it.copy(errors = emptyList()) }
            _addMovieSuccess.value = true
        } else {
            _addMovieState.update { it.copy(errors = errors) }
            _addMovieSuccess.value = false
        }
    }

    // Update form input state for UI
    fun updateFormState(
        id: String? = null,
        title: String? = null,
        director: String? = null,
        price: String? = null,
        releaseDate: String? = null,
        duration: String? = null,
        genre: String? = null,
        isFavorite: Boolean? = null
    ) {
        _addMovieState.update { current ->
            current.copy(
                id = id ?: current.id,
                title = title ?: current.title,
                director = director ?: current.director,
                price = price ?: current.price,
                releaseDate = releaseDate ?: current.releaseDate,
                duration = duration ?: current.duration,
                genre = genre ?: current.genre,
                isFavorite = isFavorite ?: current.isFavorite
            )
        }
    }

    fun toggleFavorite(movie: Movie) {
        val updatedMovie = movie.copy(isFavorite = !movie.isFavorite)
        viewModelScope.launch {
            repository.updateMovie(updatedMovie)
        }
    }

    fun resetSuccessState() {
        _addMovieSuccess.value = false
    }

    // CRUD operations
    private fun insert(movie: Movie) = viewModelScope.launch { repository.addMovie(movie) }
    fun update(movie: Movie) = viewModelScope.launch { repository.updateMovie(movie) }
    fun delete(movie: Movie) = viewModelScope.launch { repository.deleteMovie(movie) }
}