package com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.view.screens.movie


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.items
import com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.model.movie.Movie
import com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.viewmodel.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieHomeScreen(
    navController: NavController,
    viewModel: MovieViewModel = viewModel()
) {
    var showFavorites by remember { mutableStateOf(false) }
    val movies by if (showFavorites) viewModel.favoriteMovies.observeAsState(emptyList())
    else viewModel.allMovies.observeAsState(emptyList())
    var selectedMovie by remember { mutableStateOf<Movie?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movie List") },
                actions = {
                    IconButton(onClick = { navController.navigate("add_movie") }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Movie")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { showFavorites = false },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Display All Movies")
                }
                Spacer(Modifier.width(16.dp))
                Button(
                    onClick = { showFavorites = true },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Display Favorites")
                }
            }

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(movies) { movie ->
                    MovieItem(
                        movie = movie,
                        onEdit = { navController.navigate("edit_movie/${movie.id}") },
                        onDelete = { viewModel.delete(movie) },
                        onToggleFavorite = { viewModel.toggleFavorite(movie) },
                        modifier = Modifier
                            .clickable { selectedMovie = movie }
                            .background(
                                if (movie == selectedMovie) MaterialTheme.colorScheme.secondaryContainer
                                else Color.Transparent
                            )
                    )
                }
            }
            selectedMovie?.let { movie ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(16.dp)
                ) {
                    Column {
                        Text(movie.title, style = MaterialTheme.typography.headlineMedium)
                        Text("Director: ${movie.director}", style = MaterialTheme.typography.titleMedium)
                        Text("Genre: ${movie.genre}", style = MaterialTheme.typography.bodyMedium)
                        Text("Release: ${movie.releaseDate} | ${movie.duration} min", style = MaterialTheme.typography.bodyMedium)
                        Text("Price: ${movie.price}", style = MaterialTheme.typography.bodyMedium)
                        Text(if (movie.isFavorite) "Favorite" else "", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}
