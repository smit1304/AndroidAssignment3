package com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.view.screens.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.smitpatelsanjeevchauhan_comp304_001_assignment3.model.product.Product

@Composable
fun ProductItem(
    product: Product,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(product.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "ID: ${product.id}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text("$${product.price}", style = MaterialTheme.typography.bodyMedium)
                Text(
                    "Quantity: ${product.quantity}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(product.category, style = MaterialTheme.typography.bodySmall)
                Text(
                    "Delivery: ${product.deliveryDate}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Row {
                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier.testTag("favorite_button")
                ) {
                    Icon(
                        imageVector = if (product.isFavorite) Icons.Default.Favorite
                        else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (product.isFavorite) Color.Red
                        else MaterialTheme.colorScheme.onSurface
                    )
                }

                IconButton(
                    onClick = onEdit,
                    modifier = Modifier.testTag("edit_button")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit product"
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.testTag("delete_button")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete product"
                    )
                }
            }
        }
    }
}


//Icon(
//imageVector = if (product.isFavorite) Icons.Default.Favorite
//else Icons.Outlined.FavoriteBorder,
//contentDescription = "Favorite",
//tint = if (product.isFavorite) Color.Red
//else MaterialTheme.colorScheme.onSurface
//)