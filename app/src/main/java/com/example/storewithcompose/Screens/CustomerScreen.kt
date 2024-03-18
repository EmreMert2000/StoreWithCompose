package com.example.storewithcompose.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.storewithcompose.Row.ProductRow
import com.example.storewithcompose.ViewModel.AddProductViewModel
import com.example.storewithcompose.ViewModel.CustomerViewModel
import com.example.storewithcompose.data.Product

@Composable
fun CustomerScreen(viewModel: CustomerViewModel = hiltViewModel()) {


    val products = viewModel.products.value

    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        //SearchBox


        Text(
            text = "Müşteri Ekranı",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyLarge
        )

       CustomerList(products = products) {
           selectedProducts ->

       }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "İyi Alışverişler!",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyLarge
        )
    }



}





