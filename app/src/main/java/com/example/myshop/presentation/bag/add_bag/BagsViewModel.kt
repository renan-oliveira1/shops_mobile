package com.example.myshop.presentation.bag.add_bag

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.domain.use_case.bag.BagUseCases
import com.example.myshop.domain.use_case.util.OrderType
import com.example.myshop.domain.use_case.util.BagOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BagsViewModel @Inject constructor(
    private val bagUseCases: BagUseCases
):ViewModel()  {
    private val _state = mutableStateOf(BagsState())
    val state: State<BagsState> = _state

    private var recentlyDeletedBag: String? = null

    private var getShopsJob: Job? = null

    init{
        getBags(BagOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: BagsEvent){
        when(event){
            is BagsEvent.Order -> {
                getBags(event.bagOrder)
            }
            is BagsEvent.DeleteShop -> {
                viewModelScope.launch {
                    bagUseCases.deleteBag(event.bag)
                    recentlyDeletedBag = event.bag.title
                }
            }
            is BagsEvent.RestoreShop -> {
                viewModelScope.launch {
                    bagUseCases.addBag(recentlyDeletedBag?: return@launch)
                    recentlyDeletedBag = null
                }
            }

        }
    }

    fun addBag(titleBag: String){
        viewModelScope.launch {
            bagUseCases.addBag(titleBag)
            getBags(BagOrder.Date(OrderType.Descending))
        }
    }

    private fun getBags(bagOrder: BagOrder){
        getShopsJob?.cancel()
        getShopsJob = bagUseCases.getBags(bagOrder)
            .onEach { bags ->
                _state.value = state.value.copy(
                    shops = bags,
                    bagOrder = bagOrder
                )
            }
            .launchIn(viewModelScope)
    }
}