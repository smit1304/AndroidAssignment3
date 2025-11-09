package com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.view.screens.movieAppScreens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.viewmodel.MovieViewModel

@Composable
fun EditMovieScreen(
    navController: NavController,
    movieId: Int?,
    viewModel: MovieViewModel = viewModel()
) {
    val movies by viewModel.allMovies.observeAsState(emptyList())
    if (movieId == null) {
        navController.popBackStack()
        return
    }
    val movie = movies.find { it.id == movieId }
    if (movie == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    var editedTitle by remember { mutableStateOf(movie.title) }
    var editedDirector by remember { mutableStateOf(movie.director) }
    var editedPrice by remember { mutableStateOf(movie.price.toString()) }
    var editedReleaseDate by remember { mutableStateOf(movie.releaseDate) }
    var editedDuration by remember { mutableStateOf(movie.duration.toString()) }
    var editedGenre by remember { mutableStateOf(movie.genre) }
    var editedFavorite by remember { mutableStateOf(movie.isFavorite) }
    var expanded by remember { mutableStateOf(false) }
    val genres = listOf("Family", "Comedy", "Thriller", "Action")

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(value = editedTitle, onValueChange = { editedTitle = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = editedDirector, onValueChange = { editedDirector = it }, label = { Text("Director") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = editedPrice, onValueChange = { editedPrice = it }, label = { Text("Price") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = editedDuration, onValueChange = { editedDuration = it }, label = { Text("Duration") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), modifier = Modifier.fillMaxWidth())
        GenreDropdown(selectedGenre = editedGenre, onGenreSelected = { editedGenre = it }, genres = genres)
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 16.dp)) {
            Text("Favorite")
            Spacer(Modifier.width(8.dp))
            Switch(checked = editedFavorite, onCheckedChange = { editedFavorite = it })
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                onClick = {
                    viewModel.delete(movie)
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Icon(Icons.Default.Delete, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Delete")
            }
            Button(
                onClick = {
                    val updatedMovie = movie.copy(
                        title = editedTitle,
                        director = editedDirector,
                        price = editedPrice.toDoubleOrNull() ?: movie.price,
                        duration = editedDuration.toIntOrNull() ?: movie.duration,
                        genre = editedGenre,
                        isFavorite = editedFavorite
                    )
                    viewModel.update(updatedMovie)
                    navController.popBackStack()
                }
            ) {
                Icon(Icons.Default.Edit, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Save")
            }
        }
    }
}
