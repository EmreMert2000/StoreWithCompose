package com.example.storewithcompose


import AddProductScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.storewithcompose.Screens.CustomerList
import com.example.storewithcompose.Screens.CustomerScreen

import com.example.storewithcompose.Screens.LoginScreen
import com.example.storewithcompose.data.Product
import com.example.storewithcompose.ui.theme.StoreWithComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoreWithComposeTheme {
                val navController = rememberNavController()

                  //LoginScreen
                NavHost(navController = navController, startDestination = "LoginScreen") {
                    composable("LoginScreen") {
                        LoginScreen(
                            onLoginClick = { navController.navigate("AddProductScreen") },
                            onCustomerClick = { navController.navigate("CustomerScreen") }
                        )
                    }
                    composable("AddProductScreen") {
                        AddProductScreen()
                    }
                        composable("CustomerScreen") {

                            CustomerScreen()

                    }
                }






            }

            }
        }
    }



