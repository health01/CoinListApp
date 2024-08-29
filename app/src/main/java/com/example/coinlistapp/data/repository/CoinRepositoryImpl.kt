package com.example.coinlistapp.data.repository

import com.example.coinlistapp.data.dto.Coin
import com.example.coinlistapp.data.dto.CoinDetail
import com.example.coinlistapp.data.source.CoinAPiService
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val apiService: CoinAPiService
) : CoinRepository {
    override suspend fun getCoins(): List<Coin> {
        return apiService.getCoins()
    }

    override suspend fun getCoinsDetail(coinId: String): CoinDetail {
        return apiService.getCoinDetail(coinId)
    }
}