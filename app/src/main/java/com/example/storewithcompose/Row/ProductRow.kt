package com.example.storewithcompose.Row

// ProductRow.kt

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.storewithcompose.data.Product


@Composable
fun ProductRow(product: Product, onProductClick: (Product) -> Unit) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onProductClick(product) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ürün Adı
        Text(
            text = product.productName,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Ürün Fiyatı
        Text(
            text = product.productPrice,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Ürün Miktarı
        Text(
            text = product.productQuantity,
            style = MaterialTheme.typography.bodyLarge
        )

        // İkonlar
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {



        }
    }
}
