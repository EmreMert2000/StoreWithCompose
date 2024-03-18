package com.example.storewithcompose.Screens

// ProductListScreen.kt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.storewithcompose.Row.ProductRow
import com.example.storewithcompose.data.Product

@Composable
fun ProductListScreen(products: List<Product>, onProductClick: (Product) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        items(products) { product ->
            ProductRow(product = product) { selectedProduct ->
                onProductClick(selectedProduct)
            }
            Divider()
        }
    }
}

