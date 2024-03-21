package com.example.storewithcompose.data

data class Product(
    val productId: String = "",
    val productName: String = "",
    val productPrice: String = "",
    val productQuantity: String = ""
) {
    // No-argument constructor
    constructor() : this("", "", "", "")
}
