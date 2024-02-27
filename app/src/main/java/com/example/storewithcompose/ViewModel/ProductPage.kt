package com.example.storewithcompose.ViewModel
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.storewithcompose.R
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.Date
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import com.example.storewithcompose.data.Product
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions


@Composable
fun AddProductScreen() {
    var productName by remember { mutableStateOf("") }
    var productQuantity by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    var products by remember { mutableStateOf(emptyList<Product>()) }

    val context = LocalContext.current

    //List Work
    var selectedProductId by remember { mutableStateOf<String?>(null) }
    var isUpdateButtonEnabled by remember { mutableStateOf(false) }
    var isDeleteButtonEnabled by remember { mutableStateOf(false) }


    //Firebase Database :add to item

    val db = FirebaseFirestore.getInstance()
    val productsCollection = db.collection("products")



    


    val getContentLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri = it
                // Call the function to upload the selected image to Firebase
               uploadImageToFirebase(uri = it)
            }
        }
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(context,"Galeri Erişimi kabul edildi",Toast.LENGTH_SHORT).show()
                openGallery(getContentLauncher)
            } else {
                // İzin reddedildiğinde
                 Toast.makeText(context, "Galeri erişimi reddedildi.", Toast.LENGTH_SHORT).show()
            }
        }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {


        // Product Image

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.Gray)
                .clickable {
                    if (checkGalleryPermission(context = context)) {
                        openGallery(getContentLauncher)
                    } else {
                        requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                }
        ) {


            Image(
                painter = imageUri?.let { painterResource(id = R.drawable.ic_launcher_background) }
                    ?: painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Product Name
        OutlinedTextField(
            value = productName,
            onValueChange = { productName = it },
            label = { Text(text = "Product Name") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Product Price
        OutlinedTextField(
            value = productPrice,
            onValueChange = { productPrice = it },
            label = { Text(text = "Product Price") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Product Quantity
        OutlinedTextField(
            value = productQuantity,
            onValueChange = { productQuantity = it },
            label = { Text(text = "Product Quantity") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                saveProductToFirestore(
                    productsCollection = productsCollection,
                    productName = productName,
                    productPrice = productPrice,
                    productQuantity = productQuantity


                )


                Toast.makeText(context, "Ürün Başarıyla Eklendi!", Toast.LENGTH_SHORT).show()

            },

            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "KAYDET")

        }
        Button(onClick = {
            updateProductInFirestore(
                productsCollection=productsCollection,
                productId =selectedProductId.toString(),
                updatedProductName=productName,
                updatedProductPrice=productPrice,
                updatedProductQuantity=productQuantity

            )
            Toast.makeText(context,"Ürün başarıyla güncellendi!",Toast.LENGTH_SHORT).show()
            isUpdateButtonEnabled=false
        }, enabled =isUpdateButtonEnabled,
            modifier = Modifier
                .fillMaxWidth()

            ) {
            Text(text = "GÜNCELLE")
        }
        Spacer(modifier = Modifier.height(6.dp))
        Button(
            onClick = {
                selectedProductId?.let { productId ->
                    deleteProductFromFirestore(
                        productsCollection = productsCollection,
                        productId = productId,
                        onDeleteSuccess = {
                            Toast.makeText(context, "Ürün başarıyla silindi!", Toast.LENGTH_SHORT).show()
                            isDeleteButtonEnabled=false
                        },
                        onDeleteFailure = { e ->
                            Toast.makeText(context, "Ürün silinirken bir hata oluştu.", Toast.LENGTH_SHORT).show()
                            e.printStackTrace()
                        }
                    )
                }
            },
            enabled = isDeleteButtonEnabled,
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Text(text = "ÜRÜNÜ SİL")
        }



        //check to database compose...

        Spacer(modifier = Modifier.height(10.dp))

        fetchProducts(productsCollection){update ->
            products = update
        }
        Text(
            text = "Ürünlerim",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        ShowProductScreen(products = products) { selectedProduct ->
            productName = selectedProduct.productName
            productPrice = selectedProduct.productPrice
            productQuantity = selectedProduct.productQuantity
            selectedProductId = selectedProduct.productId

            isUpdateButtonEnabled = true
            isDeleteButtonEnabled=true
        }




    }
}






// Open gallery

fun openGallery(getContentLauncher: ActivityResultLauncher<String>) {
    getContentLauncher.launch("image/*")
}
// Check gallery permission
fun checkGalleryPermission(context: Context): Boolean {
    val permissionStatus = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    return permissionStatus == PackageManager.PERMISSION_GRANTED
}



fun handleProductClick(product: Product, onProductSelected: (String, String, String, String) -> Unit) {
    onProductSelected(product.productId, product.productName, product.productPrice, product.productQuantity)
}



fun uploadImageToFirebase(uri: Uri) {
    // Firebase Storage referansı oluştur
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference

    // Resmin yüklenmesi için benzersiz bir isim oluştur
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_${timeStamp}_"
    val storagePath = "images/$imageFileName.jpg"

    // Resmi Firebase Storage'a yükle
    val imageRef = storageRef.child(storagePath)
    val uploadTask = imageRef.putFile(uri)

    // Yükleme işlemi tamamlandığında
    uploadTask.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            // Resmin yüklendiği URL'i al ve Firebase veritabanına kaydet
            imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                val imageUrl = downloadUri.toString()

            }
        } else {
            // Hata durumunda kullanıcıya bilgi ver
            //Toast.makeText(LocalContext.current, "Resim yükleme başarısız.", Toast.LENGTH_SHORT).show()
        }
    }
}


fun saveProductToFirestore(
    productsCollection: CollectionReference,
    productName: String,
    productPrice: String,
    productQuantity: String
) {
    // Yeni bir ürün belgesi oluştur
    val product = hashMapOf(
        "productName" to productName,
        "productPrice" to productPrice,
        "productQuantity" to productQuantity,
        "timestamp" to FieldValue.serverTimestamp()
    )

    // Firestore koleksiyonuna ürün bilgilerini ekle
    productsCollection.add(product)
}

fun updateProductInFirestore(
    productsCollection: CollectionReference,
    productId:String,
    updatedProductName: String,
    updatedProductPrice: String,
    updatedProductQuantity: String
) {
    val productRef = productsCollection.document(productId)

    val updatedProduct = hashMapOf(
        "productName" to updatedProductName,
        "productPrice" to updatedProductPrice,
        "productQuantity" to updatedProductQuantity,
        "timestamp" to FieldValue.serverTimestamp()
    )


    productRef.set(updatedProduct, SetOptions.merge())
        .addOnSuccessListener {

        }
        .addOnFailureListener { e ->

            e.printStackTrace()
        }


}
fun deleteProductFromFirestore(
    productsCollection: CollectionReference,
    productId: String,
    onDeleteSuccess: () -> Unit,
    onDeleteFailure: (Exception) -> Unit
) {
    val productRef = productsCollection.document(productId)

    productRef.delete()
        .addOnSuccessListener {
            onDeleteSuccess()
        }
        .addOnFailureListener { e ->
            onDeleteFailure(e)
        }
}

@Composable
fun ShowProductScreen(products: List<Product>, onProductClick: (Product) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        items(products) { product ->
            ProductRow(product = product, onProductClick = onProductClick)

            Divider()
        }
    }
}




fun fetchProducts(productsCollection: CollectionReference, onProductsFetched: (List<Product>) -> Unit) {
    val productList = mutableListOf<Product>()
    productsCollection.get()
        .addOnSuccessListener { documents ->
            productList.clear()
            for (document in documents) {
                val productId = document.id
                val productName = document.getString("productName") ?: ""
                val productPrice = document.getString("productPrice") ?: ""
                val productQuantity = document.getString("productQuantity") ?: ""

                val product = Product(
                    productId = productId,
                    productName = productName,
                    productPrice = productPrice,
                    productQuantity = productQuantity
                )

                productList.add(product)
            }


            onProductsFetched(productList)
        }
        .addOnFailureListener { exception ->
            exception.printStackTrace()
        }
}

@Composable
fun ProductRow(product: Product, onProductClick: (Product) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                onProductClick(product)
            },
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












