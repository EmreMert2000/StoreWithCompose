
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.storewithcompose.ViewModel.AddProductViewModel
import com.example.storewithcompose.Screens.ProductListScreen




@Composable
fun AddProductScreen(viewModel: AddProductViewModel = hiltViewModel()) {
    var productId by remember { mutableStateOf("") }
    var productName by remember { mutableStateOf("") }
    var productQuantity by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }

    var clickedAdd by remember { mutableStateOf(false) }
    var clickedDelete by remember{ mutableStateOf(false) }
    var clickedUpdate by remember { mutableStateOf(false) }
    var clickedList by remember { mutableStateOf(false) }

    val (refreshProducts, setRefreshProducts) = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Product Name
        OutlinedTextField(
            value = productName,
            onValueChange = {newValue -> productName = newValue  },
            label = { Text(text = "Product Name") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Product Price
        OutlinedTextField(
            value = productPrice,
            onValueChange = {newValue -> productPrice = newValue },
            label = { Text(text = "Product Price") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Product Quantity
        OutlinedTextField(
            value = productQuantity,
            onValueChange = { newValue -> productQuantity = newValue },
            label = { Text(text = "Product Quantity") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Save Product Button
        Button(
            onClick = {
                clickedAdd=true


            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "KAYDET")
        }
        Spacer(modifier = Modifier.height(6.dp))
        Button(
            onClick = {
             clickedUpdate=true

            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "GÜNCELLE")
        }
        Spacer(modifier = Modifier.height(6.dp))
        Button(
            onClick = {
                 clickedDelete=true
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "SİL")
        }
        Spacer(modifier = Modifier.height(6.dp))
        Button(
            onClick = {
                clickedList=true

            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Ürünleri Göster")
        }
        // Product List
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Ürünlerim",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Show Product List
        ProductListScreen(products = viewModel.products.value ?: emptyList()) { selectedProduct ->
            productId=selectedProduct.productId
            productName = selectedProduct.productName
            productPrice = selectedProduct.productPrice
            productQuantity = selectedProduct.productQuantity
        }

        LaunchedEffect(Unit) {
            viewModel.fetchProducts()
        }
        LaunchedEffect(clickedAdd) {
            viewModel.saveProductToFirestore(
                productName = productName,
                productPrice = productPrice,
                productQuantity = productQuantity
            )

        }
        LaunchedEffect(clickedList) {
            viewModel.fetchProducts()
        }
        LaunchedEffect(clickedDelete) {
            viewModel.deleteFromFirebase(productId)



        }
        LaunchedEffect(clickedUpdate) {
            viewModel.updateProductInFirestore(productId,productName,productPrice,productQuantity)

        }

    }
}

