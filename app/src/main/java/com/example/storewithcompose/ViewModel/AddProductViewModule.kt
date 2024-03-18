package com.example.storewithcompose.ViewModel

// AddProductViewModel.kt

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storewithcompose.data.Product
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class AddProductViewModel @Inject constructor(private val productsCollection: CollectionReference) : ViewModel() {

    private val _productName = MutableStateFlow("")
    val productName: StateFlow<String> = _productName

    private val _productPrice = MutableStateFlow("")
    val productPrice: StateFlow<String> = _productPrice

    private val _productQuantity = MutableStateFlow("")
    val productQuantity: StateFlow<String> = _productQuantity

    // Products Class
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    suspend fun saveProductToFirestore(productName: String, productPrice: String, productQuantity: String) {
        if (productName.isBlank() || productPrice.isBlank() || productQuantity.isBlank()) {

            return
        }
        try {
            val productId = UUID.randomUUID().toString()
            val product = Product(productId, productName, productPrice, productQuantity)

            productsCollection
                .add(product)
                .addOnSuccessListener { documentReference ->
                    Log.d("Firestore", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error adding document", e)
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


  suspend fun deleteFromFirebase(productId: String) {
      viewModelScope.launch {
          try {
              productsCollection.document(productId).delete().await()
          } catch (e: Exception) {
              Log.e("Firestore", "Error deleting product", e)
          }
      }
  }

    //Update Functions Firebase
    suspend fun updateProductInFirestore(productId: String, updatedProductName: String, updatedProductPrice: String, updatedProductQuantity: String) {
        try {
            val productRef = productsCollection.document(productId)
            productRef.update(
                mapOf(
                    "productName" to updatedProductName,
                    "productPrice" to updatedProductPrice,
                    "productQuantity" to updatedProductQuantity
                )
            ).await()

        } catch (e: Exception) {
            Log.e("Firestore", "Error updating document", e)

        }
    }





    suspend fun fetchProducts() {
        try {
            val documents = productsCollection.get().await()
            val productList = mutableListOf<Product>()

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

            _products.value = productList
        } catch (e: Exception) {


        }
    }

}




