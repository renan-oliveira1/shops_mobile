package com.example.myshop.presentation.bag.bag_items

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.example.myshop.presentation.bag.bag_items.components.ItemInBag
import com.example.myshop.presentation.util.Screen
import com.example.myshop.ui.theme.Blue
import com.example.myshop.ui.theme.BlueDarker
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BagItemsScreen(
    navController: NavController,
    bagItemsViewModel: BagItemsViewModel = hiltViewModel()
){
    val stateBag = bagItemsViewModel.stateBag.value
    val stateItems = bagItemsViewModel.stateItems.value
    val stateTotalValue = bagItemsViewModel.stateTotalValue.value
    val scaffoldState = remember{ SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var showDialog by remember { mutableStateOf(false) }
    var itemName by remember { mutableStateOf("") }
    var itemPrice by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }
    var idItemUpdate: Int? = null

    LaunchedEffect(key1 = true){
        bagItemsViewModel.showMessageSnackBar.collectLatest { event ->
            scaffoldState.showSnackbar(message = event.message)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = scaffoldState)},
        topBar = { TopAppBar(
            title = {
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    if (stateBag != null) {
                        Text(text = stateBag.title, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigate(Screen.BagScreen.route)}) {
                    Icon(Icons.Filled.ArrowBack, "backIcon", tint = Color.White)
                }
            },
            modifier = Modifier.background(MaterialTheme.colorScheme.primary),
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                BlueDarker
            )

        )},
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialog = true
                },
                containerColor = Blue
            ) {
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Add shop")
            }
        },
        content = {
            paddingValues ->
                if (stateBag != null && stateItems != null) {
                    Column(
                        modifier = Modifier
                            .padding(top = paddingValues.calculateTopPadding(), bottom = paddingValues.calculateBottomPadding())
                    ) {
                        LazyColumn(){
                            items(stateItems){
                                item  ->
                                    ItemInBag(
                                        item = item,
                                        modifier = Modifier.clickable {
                                            itemName = item.name
                                            itemPrice = String.format("%.2f", item.price)
                                            itemQuantity = item.quantity.toString()
                                            idItemUpdate = item.id
                                            showDialog = true
                                        },
                                        onDeleteClick = {
                                            bagItemsViewModel.onEvent(ItemEvent.DeleteShop(item))
                                            scope.launch{
                                                val result = scaffoldState.showSnackbar(
                                                    message = "Item Deleted",
                                                    actionLabel = "Undo",
                                                    duration = SnackbarDuration.Short
                                                )

                                                if(result == SnackbarResult.ActionPerformed){
                                                    bagItemsViewModel.onEvent(ItemEvent.RestoreShop)
                                                }
                                            }
                                        }
                                    )
                            }
                        }

                        //dialog to add item
                        if (showDialog) {
                            AlertDialog(
                                onDismissRequest = {
                                    showDialog = false
                                },
                                title = {
                                    Text(text = "Add Bag")
                                },
                                text = {
                                    Column {
                                        Row(modifier = Modifier.fillMaxWidth()) {
                                            TextField(
                                                value = itemName,
                                                onValueChange = { itemName = it },
                                                label = { Text("Name of the item") },
                                                modifier = Modifier
                                                    .padding(5.dp)
                                                    .background(Color.Transparent),
                                                textStyle = TextStyle(color = Color.Black),
                                                shape = RoundedCornerShape(30.dp),
                                                colors = TextFieldDefaults.textFieldColors(
                                                    disabledTextColor = Color.Transparent,
                                                    focusedIndicatorColor = Color.Transparent,
                                                    unfocusedIndicatorColor = Color.Transparent,
                                                    disabledIndicatorColor = Color.Transparent
                                                )
                                            )
                                        }
                                        TextField(
                                            value = itemPrice,
                                            onValueChange = { itemPrice = it },
                                            label = { Text("Price of the item") },
                                            keyboardOptions = KeyboardOptions(
                                                keyboardType = KeyboardType.Number,
                                            ),
                                            modifier = Modifier.padding(5.dp),
                                            shape = RoundedCornerShape(30.dp),
                                            colors = TextFieldDefaults.textFieldColors(
                                                disabledTextColor = Color.Transparent,
                                                focusedIndicatorColor = Color.Transparent,
                                                unfocusedIndicatorColor = Color.Transparent,
                                                disabledIndicatorColor = Color.Transparent
                                            )

                                        )
                                        TextField(
                                            value = itemQuantity,
                                            onValueChange = { itemQuantity = it },
                                            label = { Text("Quantity of the item") },
                                            keyboardOptions = KeyboardOptions(
                                                keyboardType = KeyboardType.Number,
                                            ),
                                            modifier = Modifier.padding(5.dp),
                                            shape = RoundedCornerShape(30.dp),
                                            colors = TextFieldDefaults.textFieldColors(
                                                disabledTextColor = Color.Transparent,
                                                focusedIndicatorColor = Color.Transparent,
                                                unfocusedIndicatorColor = Color.Transparent,
                                                disabledIndicatorColor = Color.Transparent
                                            )
                                        )
                                    }
                                },
                                confirmButton = {
                                    Button(
                                        onClick = {
                                            bagItemsViewModel.onEvent(ItemEvent.SaveItem(itemName, itemPrice, itemQuantity, idItemUpdate))
                                            itemName = ""
                                            itemPrice = ""
                                            itemQuantity = ""
                                            showDialog = false
                                            idItemUpdate = null
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Blue, contentColor = Color.White)
                                    ) {
                                        Text("Confirm")
                                    }
                                },
                                dismissButton = {
                                    Button(
                                        onClick = {
                                            showDialog = false
                                            itemName = ""
                                            itemPrice = ""
                                            itemQuantity = ""
                                            showDialog = false
                                            idItemUpdate = null
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Blue, contentColor = Color.White)
                                    ) {
                                        Text("Cancel")
                                    }
                                }
                            )
                        }
                    }
                }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Blue)
                    .border(BorderStroke(1.dp, Color.White))

            ){
                Text(
                    text = "Total Value: ${String.format("%.2f", stateTotalValue)}",
                    modifier = Modifier.padding(10.dp),
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}