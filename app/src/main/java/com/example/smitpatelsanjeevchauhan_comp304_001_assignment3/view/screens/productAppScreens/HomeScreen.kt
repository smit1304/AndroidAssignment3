package com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.view.screens.productAppScreens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.model.Product
import com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.viewmodel.ProductViewModel

// Main screen displaying all products
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: ProductViewModel = viewModel(),
    windowSizeClass: WindowSizeClass = calculateWindowSizeClass(LocalActivity.current)
) {
    val products by viewModel.allProducts.observeAsState(emptyList())
    println("HomeScreen products: $products") // Log to verify model
    val isExpandedScreen = windowSizeClass.widthSizeClass != WindowWidthSizeClass.Compact
    var selectedProduct by remember { mutableStateOf< Product?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product List") },
                actions = {
                    IconButton(onClick = { navController.navigate("favorites") }) {
                        Icon(Icons.Default.Favorite, contentDescription = "Favorites")
                    }
                    IconButton(onClick = { navController.navigate("add") }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Product")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (isExpandedScreen) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(products) { product ->
                        ProductItem(
                            product = product,
                            onEdit = {
                                navController.navigate("edit/${product.id}")
                            },
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
                            Text(product.name, style = MaterialTheme.typography.headlineMedium)
                            Spacer(Modifier.height(16.dp))
                            Text("Price: $${product.price}", style = MaterialTheme.typography.titleMedium)
                            Text("Category: ${product.category}", style = MaterialTheme.typography.bodyLarge)
                            Text("Delivery Date: ${product.deliveryDate}", style = MaterialTheme.typography.bodyMedium)
                        }
                    } ?: Text(
                        "Select a product",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        } else {
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(products) { product ->
                    ProductItem(
                        product = product,
                        onEdit = { navController.navigate("edit/${product.id}") },
                        onDelete = { viewModel.delete(product) },
                        onToggleFavorite = { viewModel.toggleFavorite(product) },
                        modifier = Modifier
                            .background(
                                if (product == selectedProduct)
                                    MaterialTheme.colorScheme.secondaryContainer
                                else Color.Transparent
                            )
                    )
                }
            }
        }
    }
}