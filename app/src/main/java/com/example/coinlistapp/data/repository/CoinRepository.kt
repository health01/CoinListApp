package com.example.coinlistapp.data.repository

import com.example.coinlistapp.data.dto.Coin
import com.example.coinlistapp.data.dto.CoinDetail


interface CoinRepository {
    suspend fun getCoins(): List<Coin>
    suspend fun getCoinsDetail(coinId: String): CoinDetail
}