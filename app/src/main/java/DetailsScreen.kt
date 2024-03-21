

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.storewithcompose.ViewModel.CustomerViewModel
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.storewithcompose.data.Product






@Composable
fun DetailsScreen(productId: String, viewModel: CustomerViewModel = hiltViewModel()) {
    var product by remember { mutableStateOf<Product?>(null) }
    var navigateBack by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        viewModel.getProductDetails(productId,
            onSuccess = { fetchedProduct ->

                product = fetchedProduct
            },
            onFailure = { exception ->


            }
        )
    }


    //Box to Button
    val onBackPressed: () -> Unit = {
        navigateBack = true
    }
    BackHandler(onBack = onBackPressed)


    if (navigateBack) {

        // navController.navigate("CustomerScreen")
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {

    if (product == null) {

        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp)


        )
    } else {
        // Ürün detaylarını göster
        product?.let { product ->
            Column(
                modifier = Modifier
                    .width(300.dp) // Detaylar ekranının genişliği
                    .background(color = Color.White)
                    .padding(16.dp)
            )  {
                Text(text = "Ürün Adı: ${product.productName}")
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Ürün Fiyatı: ${product.productPrice}")
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Ürün Adeti: ${product.productQuantity}")

                Spacer(modifier = Modifier.height(16.dp))
                Text("Ürünümüzle ilgilendiğiniz için teşekkür ederiz!")

                Button(
                    onClick = { onBackPressed.invoke() },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Geri Dön")
                }
            }
        }
    }
}
}


