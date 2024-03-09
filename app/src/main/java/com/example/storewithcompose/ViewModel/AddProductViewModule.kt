package com.example.storewithcompose.ViewModel

// AddProductViewModel.kt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.storewithcompose.data.Product
import com.google.firebase.firestore.CollectionReference
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(private val productsCollection: CollectionReference) : ViewModel() {


    private val _productName = MutableStateFlow("")
    val productName: StateFlow<String> = _productName

    private val _productPrice = MutableStateFlow("")
    val productPrice: StateFlow<String> = _productPrice

    private val _productQuantity = MutableStateFlow("")
    val productQuantity: StateFlow<String> = _productQuantity

    //Products Class
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products


    suspend fun saveProductToFirestore() {
        viewModelScope.launch {
            val productNameValue = _productName.value
            val productPriceValue = _productPrice.value
            val productQuantityValue = _productQuantity.value

            val product = hashMapOf(
                "productName" to productNameValue,
                "productPrice" to productPriceValue,
                "productQuantity" to productQuantityValue

            )


            productsCollection.add(product)
        }
    }
}


