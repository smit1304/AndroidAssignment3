package com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.view.screens.movie


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movie List") },
                actions = {
                    IconButton(onClick = { navController.navigate("add_movie") }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Movie")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Button"
                        )
                    }
                },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            Row(
                modifier = Modifier
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
                    )
                }
            }
        }
    }
}

