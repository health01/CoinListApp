package com.example.coinlistapp.domain.repository.usecase

import com.example.coinlistapp.data.DataState
import com.example.coinlistapp.data.dto.Coin
import com.example.coinlistapp.data.dto.CoinDetail
import com.example.coinlistapp.data.network.CoinAPiService
import com.example.coinlistapp.data.repository.CoinRepositoryImpl
import com.google.gson.JsonParseException
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

class CoinUseCaseTest {

    @MockK
    private lateinit var coinApi: CoinAPiService
    private lateinit var coinRepositoryImpl: CoinRepositoryImpl
    private lateinit var coinUseCase: CoinUseCase
    private val coinId = "BTC"
    private val jsonParseExceptionMessage = "Json Parse Exception"

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        coinRepositoryImpl = CoinRepositoryImpl(coinApi)
        coinUseCase = CoinUseCase(coinRepositoryImpl)
    }

    @Test
    fun `test getCoinById`() = runTest {
        // Given
        val expectedCoinDetail = mockk<CoinDetail>(relaxed = true) {
            coEvery { id } returns coinId
        }
        coEvery { coinRepositoryImpl.getCoinsDetail(coinId) } returns expectedCoinDetail

        // When
        val flow = coinUseCase.getCoinById(coinId)
        val dataState = flow.toList().last()

        // Then
        assertEquals(DataState.Success(expectedCoinDetail), dataState)
        assertEquals(coinId, (dataState as DataState.Success).data.id)
    }

    @Test
    fun `test getCoinById IOException`() = runTest {
        // Given
        val ioException = IOException()
        coEvery { coinRepositoryImpl.getCoinsDetail(coinId) } throws ioException

        // When
        val flow = coinUseCase.getCoinById(coinId)
        val dataState = flow.toList().last()

        // Then
        assertEquals(DataState.Error(400, "IOException"), dataState)
    }

    @Test
    fun `test getCoinById JsonParseException`() = runTest {
        // Given
        val httpExceptionMessage = "Json Parse Exception"
        val httpException = JsonParseException(httpExceptionMessage, null)
        coEvery { coinRepositoryImpl.getCoinsDetail(coinId) } throws httpException

        // When
        val flow = coinUseCase.getCoinById(coinId)
        val dataState = flow.toList().last()

        // Then
        assertEquals(DataState.Error(null, httpExceptionMessage), dataState)
    }

    @Test
    fun `test getCoinList`() = runTest {
        // Given
        val expectedCoins = listOf(
            Coin("BTC", "Bitcoin", "BTC", 1, isNew = false, isActive = true, "crypto"),
            Coin("ETH", "Ethereum", "ETH", 2, isNew = false, isActive = true, "crypto")
        )
        coEvery { coinRepositoryImpl.getCoins() } returns expectedCoins.sortedBy { it.name }

        // When
        val flow = coinUseCase.getCoinList()
        val dataState = flow.toList().last()

        // Then
        assertEquals(DataState.Success(expectedCoins.sortedBy { it.name }), dataState)
    }

    @Test
    fun `test getCoinList IOException`() = runTest {
        // Given
        val ioException = IOException()
        coEvery { coinRepositoryImpl.getCoins() } throws ioException

        // When
        val flow = coinUseCase.getCoinList()
        val dataState = flow.toList().last()

        // Then
        assertEquals(DataState.Error(400, "IOException"), dataState)
    }

    @Test
    fun `test getCoinList JsonParseException`() = runTest {
        // Given
        val httpException = JsonParseException(jsonParseExceptionMessage, null)
        coEvery { coinRepositoryImpl.getCoins() } throws httpException

        // When
        val flow = coinUseCase.getCoinList()
        val dataState = flow.toList().last()

        // Then
        assertEquals(DataState.Error(null, jsonParseExceptionMessage), dataState)
    }
}