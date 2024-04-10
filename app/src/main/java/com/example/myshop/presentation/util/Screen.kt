package com.example.myshop.presentation.util

sealed class Screen(val route: String) {
    object BagScreen: Screen("bag_screen")
    object AddBagScreen: Screen("add_bag_screen")
}