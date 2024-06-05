package com.example.coinlistapp.presentation.coinDetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.coinlistapp.data.DataState
import com.example.coinlistapp.data.dto.CoinDetail
import com.example.coinlistapp.data.network.CoinAPiService
import com.example.coinlistapp.data.repository.CoinRepositoryImpl
import com.example.coinlistapp.domain.repository.usecase.CoinUseCase
import com.example.coinlistapp.presentation.viewmodel.BaseTestingViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class CoinDetailViewModelTest : BaseTestingViewModel() {

    @get:Rule
    var instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private var coinApi: CoinAPiService = mockk(relaxed = true)
    private var coinRepositoryImpl: CoinRepositoryImpl = mockk(relaxed = true)
    private var coinUseCase: CoinUseCase = mockk(relaxed = true)

    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)

    private val viewModel: CoinDetailViewModel by lazy {
        CoinDetailViewModel(coinUseCase, savedStateHandle, mockDispatchers)
    }

    @Before
    fun setUp() {
        coinRepositoryImpl = CoinRepositoryImpl(coinApi)
        coinUseCase = CoinUseCase(coinRepositoryImpl)
        coEvery { savedStateHandle.get<String>(any()) } returns "BTC"
    }

    @Test
    fun `test getCoinDetail success`() = runTest(testDispatcher) {
        val expectedCoinDetail = mockk<CoinDetail>(relaxed = true)

        every { expectedCoinDetail.id } returns "btc"
        every { expectedCoinDetail.description } returns "description"
        every { expectedCoinDetail.isActive } returns true
        coEvery { coinRepositoryImpl.getCoinsDetail(any()) } returns expectedCoinDetail

        val results = mutableListOf<CoinDetailState>()
        val job = launch {
            viewModel.state.toList(results)
        }

        viewModel.getCoinDetail("btc")
        advanceUntilIdle()

        Assert.assertEquals(CoinDetailState(isLoading = false), results[0])
        Assert.assertEquals(CoinDetailState(isLoading = true), results[1])

        Assert.assertEquals(
            CoinDetailState(
                isLoading = false,
                coin = expectedCoinDetail
            ), results[2]
        )

        job.cancel()
    }

    @Test
    fun `test getCoinDetail error`() = runTest(testDispatcher) {
        val errorMessage = "Failed to fetch coin details"
        coEvery { coinRepositoryImpl.getCoinsDetail(any()) } throws Exception(errorMessage)

        val results = mutableListOf<CoinDetailState>()
        val job = launch {
            viewModel.state.toList(results)
        }

        viewModel.getCoinDetail("aaa")
        advanceUntilIdle()


        Assert.assertEquals(CoinDetailState(isLoading = false), results[0])
        Assert.assertEquals(CoinDetailState(isLoading = true), results[1])
        Assert.assertEquals(
            CoinDetailState(error = DataState.Error(null, errorMessage)),
            results[2]
        )
        job.cancel()
    }
}