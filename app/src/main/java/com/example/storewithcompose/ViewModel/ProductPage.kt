package com.example.storewithcompose.ViewModel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.storewithcompose.R


@Composable
fun AddProductScreen()
{
    var productName by remember { mutableStateOf("") }
    var productQuantity by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var ImageUri by remember { mutableStateOf("") }


    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(56.dp)
    ) {


        //Product Image
        Image(
            painter =ImageUri?.let { painterResource(id = R.drawable.ic_launcher_background) }
                ?: painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.Gray)
        )

        Spacer(modifier=Modifier.height(16.dp))


        //Product Name
        OutlinedTextField(value = productName, onValueChange ={productName = it}
           , label = { Text(text = "Ürün Adı") },
            modifier= Modifier
                .fillMaxWidth()
                .height(56.dp)

        )

        Spacer(modifier = Modifier.height(16.dp))
        //Product Price
        OutlinedTextField(value = productPrice, onValueChange ={productPrice = it}
            , label = { Text(text = "Ürün Fiyatı") },
            modifier= Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        //Product Quantity
        OutlinedTextField(value = productQuantity, onValueChange ={productQuantity = it}
            , label = { Text(text = "Ürün Adeti") },
            modifier= Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = {

        }, modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
        ) {
             Text(text = "Kaydet")




        }
        





    }
}

@Composable
fun ShowProductScreen(

)
{

}