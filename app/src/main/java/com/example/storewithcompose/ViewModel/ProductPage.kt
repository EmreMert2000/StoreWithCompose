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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import com.example.storewithcompose.data.Product
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore



@Composable
fun AddProductScreen() {
    var productName by remember { mutableStateOf("") }
    var productQuantity by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    var products by remember { mutableStateOf(emptyList<Product>()) }

    val context = LocalContext.current

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


                Toast.makeText(context, "Veri Tabanına Başarıyla Eklendi!", Toast.LENGTH_SHORT).show()
                fetchProducts(productsCollection){update ->
                    products = update
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = "Save")
        }

        //check to database compose...

        Spacer(modifier = Modifier.height(2.dp))



        ShowProductScreen(products = products)

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
                // İlgili Firebase veritabanına imageUrl'ü kaydetme işlemini gerçekleştir
                // Bu işlemi projenizin gereksinimlerine uygun şekilde yapmalısınız
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







