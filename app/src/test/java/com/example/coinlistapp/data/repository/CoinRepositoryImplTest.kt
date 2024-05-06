package com.example.coinlistapp.data.repository

import com.example.coinlistapp.data.dto.Coin
import com.example.coinlistapp.data.dto.CoinDetail
import com.example.coinlistapp.data.network.CoinAPiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CoinRepositoryImplTest {
    @MockK
    private lateinit var coinApi: CoinAPiService
    private lateinit var coinRepositoryImpl: CoinRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        coinRepositoryImpl = CoinRepositoryImpl(coinApi)
    }

    @Test
    fun `test getCoins`() = runBlocking {
        val expectedCoins = listOf(
            Coin("BTC", "Bitcoin", "BTC", 1, isNew = false, isActive = true, "crypto"),
            Coin("ETH", "Ethereum", "ETH", 2, isNew = false, isActive = true, "crypto")
        )
        coEvery { coinApi.getCoins() } returns expectedCoins

        val coins = coinRepositoryImpl.getCoins()

        assertEquals(expectedCoins, coins)
    }

    @Test
    fun `test getCoinDetail`() = runBlocking {
        val coinId = "BTC"
        val expectedCoinDetail = mockk<CoinDetail>(relaxed = true) {
            coEvery { id } returns coinId
        }
        coEvery { coinApi.getCoinDetail(coinId) } returns expectedCoinDetail

        val coinDetail = coinRepositoryImpl.getCoinsDetail(coinId)

        assertEquals(expectedCoinDetail, coinDetail)
        assertEquals(coinId, coinDetail.id)
    }

    @Test
    fun `test getCoinDetail with invalid id`() = runBlocking {
        val coinId = "INVALID_ID"
        val expectedError = RuntimeException("Coin with id $coinId not found")
        coEvery { coinApi.getCoinDetail(coinId) } throws expectedError

        val result = runCatching { coinRepositoryImpl.getCoinsDetail(coinId) }

        assertEquals(expectedError, result.exceptionOrNull())
    }
}