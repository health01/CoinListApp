package com.example.coinlistapp.ui.coinDetail

import com.example.coinlistapp.data.DataState
import com.example.coinlistapp.data.dto.CoinDetail

data class CoinDetailState(
    val isLoading: Boolean = false,
    val coin: CoinDetail? = null,
    val error: DataState.Error? = null
)