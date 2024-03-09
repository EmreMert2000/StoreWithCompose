package com.example.storewithcompose.util

// ImageUtil.kt

import android.net.Uri
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun openGallery(getContentLauncher: ActivityResultLauncher<String>) {
    getContentLauncher.launch("image/*")
}

@Composable
fun uploadImageToFirebase(uri: Uri) {
    val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference

    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_${timeStamp}_"
    val storagePath = "images/$imageFileName.jpg"

    val imageRef = storageRef.child(storagePath)
    val uploadTask = imageRef.putFile(uri)

    uploadTask.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            // Resmin yüklendiği URL'i al ve Firebase veritabanına kaydet
            imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                val imageUrl = downloadUri.toString()
                // İşlemleriniz burada gerçekleşebilir
            }
        } else {
            // Hata durumunda kullanıcıya bilgi ver
         //   val context = LocalContext.current
           // Toast.makeText(context, "Resim yükleme başarısız.", Toast.LENGTH_SHORT).show()
        }
    }
}
