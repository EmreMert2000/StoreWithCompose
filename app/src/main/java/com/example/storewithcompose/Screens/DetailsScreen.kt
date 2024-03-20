package com.example.storewithcompose.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetailsScreen(productName: String, productPrice: String, productQuantity: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Ürün Adı: $productName")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Ürün Fiyatı: $productPrice")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Ürün Adeti: $productQuantity")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Ürünümüzle ilgilendiğiniz için teşekkür ederiz!")
        Spacer(modifier=Modifier.height(6.dp))
    }
}