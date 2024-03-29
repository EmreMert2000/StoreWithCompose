package com.example.storewithcompose.ViewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storewithcompose.data.Product
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(private val firestore: FirebaseFirestore) : ViewModel() {
    private val customersCollection = firestore.collection("products")

    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products: State<List<Product>> = _products

    private val _productDetails = mutableStateOf<Product?>(null)
    val productDetails: State<Product?> = _productDetails

    fun fetchProducts() {
        customersCollection.get()
            .addOnSuccessListener { documents ->
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
            }
            .addOnFailureListener { exception ->
                Log.e("CustomerViewModel", "Error fetching products", exception)
            }
    }


    fun getProductDetails(productId: String, onSuccess: (Product) -> Unit, onFailure: (Exception) -> Unit) {
        val docRef = firestore.collection("products").document(productId)
        docRef.get()
            .addOnSuccessListener { document ->
                val product = document.toObject(Product::class.java)
                product?.let {
                    onSuccess(it)
                } ?: onFailure(Exception("Product not found"))
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }


}





