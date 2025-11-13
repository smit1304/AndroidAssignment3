package com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.view.screens.movie


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.viewmodel.MovieViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMovieScreen(
    navController: NavController,
    viewModel: MovieViewModel = viewModel()
) {
    val state = viewModel.addMovieState.collectAsState().value
    var showDatePicker by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var genre by remember { mutableStateOf("") }
    val genres = listOf("Family", "Comedy", "Thriller", "Action")
    // Date picker logic would go here or via custom DatePicker composable
    // Date Picker Dialog
    if (showDatePicker) {
        // Use the user's local "today"
        val todayLocal = LocalDate.now(ZoneId.systemDefault())

        // Get the millis for the START of the user's local "today"
        val initialMillis = todayLocal.atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = initialMillis, // <-- FIX
            // Use the local year
            yearRange = todayLocal.year..(todayLocal.year + 5),
            selectableDates = object : SelectableDates {

                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    val pickedDateUtc = Instant.ofEpochMilli(utcTimeMillis)
                        .atZone(ZoneOffset.UTC)
                        .toLocalDate()

                    // Compare the picker's UTC date against the user's LOCAL date
                    return !pickedDateUtc.isBefore(todayLocal)
                }

                override fun isSelectableYear(year: Int): Boolean {
                    // Use the local year
                    return year >= todayLocal.year
                }
            }
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val date = Instant.ofEpochMilli(millis)
                                .atZone(ZoneOffset.UTC) // <-- FIX
                                .toLocalDate()
                                .toString()
                            viewModel.updateFormState(releaseDate = date)
                        }
                        showDatePicker = false
                    }
                ) { Text("OK") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add a New Movie") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to Home"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Display errors
            state.errors.forEach { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            OutlinedTextField(
                value = state.id,
                onValueChange = { viewModel.updateFormState(id = it) },
                label = { Text("Movie ID") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )

            OutlinedTextField(
                value = state.title,
                onValueChange = { viewModel.updateFormState(title = it) },
                label = { Text("Movie Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )

            OutlinedTextField(
                value = state.director,
                onValueChange = { viewModel.updateFormState(director = it) },
                label = { Text("Director") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )

            OutlinedTextField(
                value = state.price,
                onValueChange = { viewModel.updateFormState(price = it) },
                label = { Text("Price") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )

//            OutlinedTextField(
//                value = state.releaseDate, onValueChange = { viewModel.updateFormState(releaseDate = it) },
//                label = { Text("Release Date (YYYY-MM-DD)") }, modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp)
//            )
            // Date Picker
            OutlinedButton(
                onClick = { showDatePicker = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(state.releaseDate.ifEmpty { "Select Release Date" })
            }
            OutlinedTextField(
                value = state.duration,
                onValueChange = { viewModel.updateFormState(duration = it) },
                label = { Text("Duration (min)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )

            GenreDropdown(
                selectedGenre = state.genre,
                onGenreSelected = { viewModel.updateFormState(genre = it) },
                genres = genres,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Favorite?")
                Spacer(Modifier.weight(1f))
                Switch(
                    checked = state.isFavorite,
                    onCheckedChange = { viewModel.updateFormState(isFavorite = it) }
                )
            }
            val success by viewModel.addMovieSuccess.collectAsState()

            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { viewModel.validateAndAddMovie() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Movie")
            }

            // Observe success and navigate
            LaunchedEffect(success) {
                if (success) {
                    navController.navigate("movie_home") {
                        popUpTo("add") { inclusive = true } // Corrected route name
                    }
                    viewModel.resetSuccessState()
                }
            }
        }
    }
}

