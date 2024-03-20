package com.example.storewithcompose.Screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.compose.rememberNavController
import com.example.storewithcompose.ViewModel.CustomerViewModel
import com.example.storewithcompose.data.Product

@Composable
fun CustomerScreen(viewModel: CustomerViewModel = hiltViewModel()) {
    val products = viewModel.products.value
    val navControllerCustomer = rememberNavController()

    // Arama değişkenleri
    val searchQuery = remember { mutableStateOf("") }
    val searchResults = remember { mutableStateOf<List<Product>>(emptyList()) }

    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Arama işlevi
        fun performSearch(query: String) {
            val filteredProducts = if (query.isNotBlank()) {
                products.filter { product ->
                    product.productName.contains(query, ignoreCase = true)
                }
            } else {
                emptyList()
            }
            searchResults.value = filteredProducts
        }

        // Arama kutusu
        TextField(
            value = searchQuery.value,
            onValueChange = { newValue ->
                searchQuery.value = newValue
                performSearch(newValue)
            },
            placeholder = { Text("Ürün Ara") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            singleLine = true
        )

        // Ürün listesi
        CustomerList(products = if (searchQuery.value.isNotBlank()) searchResults.value else products) { selectedProduct ->
            navControllerCustomer.navigate("DetailsScreen")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Footer
        Text(
            text = "İyi Alışverişler!",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

