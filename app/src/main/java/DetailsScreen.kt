

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateIntSizeAsState
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.storewithcompose.data.Product






@Composable
fun DetailsScreen(productId: String, viewModel: CustomerViewModel = hiltViewModel()) {
    var product by remember { mutableStateOf<Product?>(null) }
    var navigateBack by remember { mutableStateOf(false) }
val context= LocalContext.current


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

         fun openUrl(url: String, context: Context) {
            if (url.isNotBlank()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            } else {
                Log.e("openUrl", "URL is empty")
            }
        }
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
                ClickableText(
                    text = AnnotatedString("Ürünü Göster"),
                    onClick = {
                        // Open the product link
                        openUrl("http://" +product.productLink, context = context)
                    }
                )

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


