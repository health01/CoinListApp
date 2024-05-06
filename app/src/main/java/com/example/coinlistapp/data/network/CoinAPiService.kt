package com.example.coinlistapp.data.network

import com.example.coinlistapp.data.dto.Coin
import com.example.coinlistapp.data.dto.CoinDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinAPiService {
    @GET("v1/coins")
    suspend fun getCoins(): List<Coin>

    @GET("v1/coins/{coinId}")
    suspend fun getCoinDetail(
        @Path("coinId") coinId: String
    ): CoinDetail
}