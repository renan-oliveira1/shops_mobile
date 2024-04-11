package com.example.myshop.presentation.bag.bag_items

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.model.Bag
import com.example.myshop.domain.model.Item
import com.example.myshop.domain.use_case.bag.BagUseCases
import com.example.myshop.domain.use_case.items.ItemUseCases
import com.example.myshop.presentation.bag.add_bag.BagsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BagItemsViewModel @Inject constructor(
    private val bagUseCases: BagUseCases,
    private val itemsUseCases: ItemUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _stateBag = mutableStateOf<Bag?>(null)
    val stateBag: State<Bag?> = _stateBag

    private val _stateItems = mutableStateOf<List<Item>?>(null)
    val stateItems: State<List<Item>?> = _stateItems

    private val _stateTotalValue = mutableDoubleStateOf(0.00)
    val stateTotalValue: State<Double> = _stateTotalValue

    private var recentlyDeletedItem: Item? = null

    private var getShopsJob: Job? = null

    init{
        savedStateHandle.get<Int>("bagId")?.let { bagId ->
            if(bagId!=-1){
                viewModelScope.launch {
                    val bag = bagUseCases.getBag(bagId)
                    _stateBag.value = bag

                    itemsUseCases.getItemsBag(bagId).onEach { items ->
                        _stateItems.value = items
                        sumTotalValue(items)
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

    fun onEvent(itemEvent: ItemEvent){
        when(itemEvent){
            is ItemEvent.Order ->{
                viewModelScope.launch {
                    getItems()
                }
            }
            is ItemEvent.DeleteShop ->{
                viewModelScope.launch {
                    itemsUseCases.deleteItem(itemEvent.item)
                    recentlyDeletedItem = itemEvent.item
                }
            }
            is ItemEvent.RestoreShop ->{
                viewModelScope.launch {
                    recentlyDeletedItem?.let { itemsUseCases.addItem(it) }
                }
            }
        }
    }

    fun save(name: String, price: String, quantity: String, idItem: Int?){
        val item = Item(name = name, quantity = quantity.toInt(), price = price.toDouble(), bagId = stateBag.value!!.id!!, id = idItem)
        viewModelScope.launch {
            itemsUseCases.addItem(item)
            getItems()
        }
    }

    fun getItems(){
        getShopsJob?.cancel()
        getShopsJob = viewModelScope.launch {
            itemsUseCases.getItemsBag(_stateBag.value!!.id!!)
                .onEach {items ->
                    _stateItems.value = stateItems.value
                    sumTotalValue(items)
                }
                .launchIn(viewModelScope)
        }
    }

    private fun sumTotalValue(items: List<Item>){
        var sum = 0.0
        items.forEach { item ->
            sum += item.quantity*item.price
        }
        _stateTotalValue.doubleValue = sum
    }
}