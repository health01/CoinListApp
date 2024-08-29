package com.example.coinlistapp.ui.coinList

import com.example.coinlistapp.data.DataState
import com.example.coinlistapp.data.dto.Coin


data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val error: DataState.Error? = null
)