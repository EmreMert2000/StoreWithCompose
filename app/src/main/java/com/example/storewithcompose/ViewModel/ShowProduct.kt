package com.example.storewithcompose.ViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.storewithcompose.data.Product
import com.google.firebase.firestore.CollectionReference

@Composable
fun ShowProductScreen(products: List<Product>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        items(products) { product ->
            ProductRow(product = product)
            Divider()
        }
    }
}
@Composable
fun ProductRow(product: Product) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Product Name
        Text(
            text = product.productName,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Product Price
        Text(
            text = product.productPrice,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Product Quantity
        Text(
            text = product.productQuantity,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

fun fetchProducts(productsCollection: CollectionReference, onProductsFetched: (List<Product>) -> Unit) {
    val productList = mutableListOf<Product>()
    productsCollection.get()
        .addOnSuccessListener { documents ->
            productList.clear()
            for (document in documents) {
                val productName = document.getString("productName") ?: ""
                val productPrice = document.getString("productPrice") ?: ""
                val productQuantity = document.getString("productQuantity") ?: ""

                val product = Product(
                    productName = productName,
                    productPrice = productPrice,
                    productQuantity = productQuantity
                )

                productList.add(product)
            }

            // Verileri callback ile gönder
            onProductsFetched(productList)
        }
        .addOnFailureListener { exception ->
            exception.printStackTrace()
        }
}

