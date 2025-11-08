package com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSelectorScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Select App") }) }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding), contentAlignment = Alignment.Center) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    onClick = { navController.navigate("product_home") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(vertical = 16.dp)
                ) {
                    Text("ProductApp")
                }
                Button(
                    onClick = { navController.navigate("movie_home") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(vertical = 16.dp)
                ) {
                    Text("MovieApp")
                }
            }
        }
    }
}
