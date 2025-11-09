package com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.view.screens.product

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.model.product.Product
import com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.viewmodel.ProductViewModel

// Main screen displaying all products (Exercise 1 Fig 1.0)
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: ProductViewModel = viewModel(),
    windowSizeClass: WindowSizeClass = calculateWindowSizeClass(LocalActivity.current)
) {
    val products by viewModel.allProducts.observeAsState(emptyList())
    var productID by remember { mutableStateOf("") }
    val isExpandedScreen = windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    // Filter by ID if user types a valid int
    val filteredProducts = remember(products, productID) {
        val id = productID.toIntOrNull()
        if (id == null) products else products.filter { it.id == id }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product List") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Button"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("favorites") }) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Favorites")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add") }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Product")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // 🔍 Search box required by Exercise 1
            OutlinedTextField(
                value = productID,
                onValueChange = { productID = it },
                label = { Text("Search by Product ID") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            if (isExpandedScreen) {
                // Two-pane layout for large screens
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        items(filteredProducts) { product ->
                            ProductItem(
                                product = product,
                                onEdit = { navController.navigate("edit/${product.id}") },
                                onDelete = { viewModel.delete(product) },
                                onToggleFavorite = { viewModel.toggleFavorite(product) },
                                modifier = Modifier
                                    .clickable { selectedProduct = product }
                                    .background(
                                        if (product == selectedProduct)
                                            MaterialTheme.colorScheme.secondaryContainer
                                        else Color.Transparent
                                    )
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(16.dp),
                        contentAlignment = Alignment.TopStart
                    ) {
                        selectedProduct?.let { product ->
                            Column {
                                Text(
                                    product.name,
                                    style = MaterialTheme.typography.headlineMedium
                                )
                                Spacer(Modifier.height(16.dp))
                                Text(
                                    "ID: ${product.id}",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(Modifier.height(16.dp))
                                Text(
                                    product.name,
                                    style = MaterialTheme.typography.headlineMedium
                                )
                                Spacer(Modifier.height(16.dp))
                                Text(
                                    "Price: $${product.price}",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(Modifier.height(16.dp))
                                Text(
                                    "Price: $${product.quantity}",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    "Category: ${product.category}",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    "Delivery Date: ${product.deliveryDate}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        } ?: Text(
                            "Select a product",
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            } else {
                // Single-pane layout for phones
                LazyColumn {
                    items(filteredProducts) { product ->
                        ProductItem(
                            product = product,
                            onEdit = { navController.navigate("edit/${product.id}") },
                            onDelete = { viewModel.delete(product) },
                            onToggleFavorite = { viewModel.toggleFavorite(product) }
                        )
                    }
                }
            }
        }
    }
}
