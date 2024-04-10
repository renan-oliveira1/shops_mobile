package com.example.myshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myshop.presentation.bag.add_bag.BagScreen
import com.example.myshop.presentation.bag.bag_items.BagItemsScreen
import com.example.myshop.presentation.util.Screen
import com.example.myshop.ui.theme.MyShopTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyShopTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.BagScreen.route
                    ){
                       composable(route=Screen.BagScreen.route){
                           BagScreen(navController = navController)
                       }
                        composable(
                            route = Screen.AddBagScreen.route + "?bagId={bagId}",
                            arguments = listOf(
                                navArgument("bagId"){
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ){
                            BagItemsScreen(navController = navController)
                        }
                    }
                }

            }
        }
    }
}