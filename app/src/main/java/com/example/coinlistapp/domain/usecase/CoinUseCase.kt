package com.example.coinlistapp.domain.usecase

import com.example.coinlistapp.data.DataState
import com.example.coinlistapp.data.dto.Coin
import com.example.coinlistapp.data.dto.CoinDetail
import com.example.coinlistapp.data.mapper.sortByName
import com.example.coinlistapp.data.repository.CoinRepository
import com.example.coinlistapp.data.source.BasicApiCall
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoinUseCase @Inject constructor(
    private val repository: CoinRepository,
) : BasicApiCall {

    fun getCoinById(coinId: String): Flow<DataState<CoinDetail>> = flow {
        emit(DataState.Loading)
        delay(100)
        emit(startApiCall { repository.getCoinsDetail(coinId) })
    }

    fun getCoinList(): Flow<DataState<List<Coin>>> = flow {
        emit(DataState.Loading)
        delay(100)
        emit(startApiCall { repository.getCoins().sortByName() })
    }
}

