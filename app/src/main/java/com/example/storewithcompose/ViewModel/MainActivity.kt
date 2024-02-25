package com.example.storewithcompose.ViewModel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.storewithcompose.ui.theme.StoreWithComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoreWithComposeTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "LoginScreen") {

                    composable("LoginScreen") { LoginScreen(onLoginClick = { navController.navigate("AddProductScreen") }) }
                    composable("AddProductScreen") { AddProductScreen() }


                //ProductScreen
                  //AddProductScreen()






            }
        }
    }
          }
}




