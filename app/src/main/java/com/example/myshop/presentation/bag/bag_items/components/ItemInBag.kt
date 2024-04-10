package com.example.myshop.presentation.bag.bag_items.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myshop.domain.model.Bag
import com.example.myshop.domain.model.Item

@Composable
fun ItemInBag(
    item: Item = Item(name = "Bolacha", quantity = 10, price = 10.0, bagId = 2),
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
){
    Card(
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column {
                Text(text = item.name,style = TextStyle(fontSize = 18.sp))
                Text(text = "Quantity: ${item.quantity}", style = TextStyle(fontSize = 12.sp))
                Text(text = "Price: ${String.format("%.2f", item.price)}", style = TextStyle(fontSize = 12.sp))
            }

            IconButton(onClick = { onDeleteClick.invoke() }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete item", tint = Color.Red)
            }

        }
    }
}