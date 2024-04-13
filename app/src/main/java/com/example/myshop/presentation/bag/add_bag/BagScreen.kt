package com.example.myshop.presentation.bag.add_bag

import android.annotation.SuppressLint
import android.content.res.Resources.Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

import com.example.myshop.presentation.bag.add_bag.components.BagItem
import com.example.myshop.presentation.util.Screen
import com.example.myshop.ui.theme.Blue
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BagScreen(
    navController: NavController,
    viewModel: BagsViewModel = hiltViewModel()
){
    val state = viewModel.state.value
    val scaffoldState = remember{SnackbarHostState()}
    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    var textValue by remember { mutableStateOf("") }
    Scaffold(
        snackbarHost = {SnackbarHost(hostState = scaffoldState)},
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialog = true
                },
                containerColor = Blue
            ) {
               Icon(imageVector = Icons.Default.Add, contentDescription = "Add shop")
            }
        }
    ){
        LazyColumn(modifier = Modifier.fillMaxSize()){
            items(state.shops){ bag ->
                BagItem(
                    bag,
                    modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(
                                Screen.AddBagScreen.route +
                                        "?bagId=${bag.id}"
                            )
                        },
                    onDeleteClick = {
                        viewModel.onEvent(BagsEvent.DeleteShop(bag))
                        scope.launch {
                            val result = scaffoldState.showSnackbar(
                                message = "Bag Deleted!",
                                actionLabel = "Undo",
                                duration = SnackbarDuration.Short
                            )
                            if(result == SnackbarResult.ActionPerformed){
                                viewModel.onEvent(BagsEvent.RestoreShop)
                            }
                        }
                    }
                )
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                },
                title = {
                    Text(text = "Add Bag")
                },
                text = {
                    TextField(
                        value = textValue,
                        onValueChange = { textValue = it },
                        label = { Text("Title of the Bag") }
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.onEvent(BagsEvent.SaveBag(textValue))
                            textValue = ""
                            showDialog = false
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