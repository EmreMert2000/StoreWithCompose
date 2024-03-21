package com.example.storewithcompose.Screens
import DetailsScreen
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.example.storewithcompose.ViewModel.CustomerViewModel
import com.example.storewithcompose.data.Product
import kotlin.system.exitProcess

@Composable
fun CustomerScreen(viewModel: CustomerViewModel = hiltViewModel()) {
    val products = viewModel.products.value
    val navControllerCustomer = rememberNavController()
    val numberId = remember { mutableStateOf("") }
    val searchQuery = remember { mutableStateOf("") }
    val searchResults = remember { mutableStateOf<List<Product>>(emptyList()) }

    val onExit: () -> Unit = {
        exitProcess(0)
    }

    val context = LocalContext.current
    BackHandler {
        onExit()
    }
    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

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

        CustomerList(products = if (searchQuery.value.isNotBlank()) searchResults.value else products) { selectedProduct ->
            numberId.value = selectedProduct.productId
            navControllerCustomer.navigate("DetailsScreen")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "İyi Alışverişler!",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onExit() // Çıkış işlemini gerçekleştir
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Çıkış Yap")
        }
    }

    // NavHost
    NavHost(navController = navControllerCustomer, startDestination = "CustomerScreen") {
        composable("CustomerScreen") { }
        composable("DetailsScreen") {
            DetailsScreen(productId = numberId.value)
        }
        composable("LoginPage") {
            LoginScreen(onLoginClick = { /*TODO*/ }) {}
        }
    }
}
