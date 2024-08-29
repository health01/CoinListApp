package com.example.coinlistapp.presentation.coinList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.coinlistapp.data.DataState
import com.example.coinlistapp.data.dto.Coin
import com.example.coinlistapp.data.repository.CoinRepositoryImpl
import com.example.coinlistapp.data.source.CoinAPiService
import com.example.coinlistapp.domain.repository.usecase.CoinUseCase
import com.example.coinlistapp.presentation.viewmodel.BaseTestingViewModel
import com.example.coinlistapp.ui.coinList.CoinListState
import com.example.coinlistapp.ui.coinList.CoinListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class CoinListViewModelTest : BaseTestingViewModel() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private var coinApi: CoinAPiService = mockk(relaxed = true)
    private var coinRepositoryImpl: CoinRepositoryImpl = mockk(relaxed = true)
    private var coinUseCase: CoinUseCase = mockk(relaxed = true)

    private val viewModel: CoinListViewModel by lazy {
        CoinListViewModel(coinUseCase, mockDispatchers)
    }

    override fun setup() {
        super.setup()
        coinRepositoryImpl = CoinRepositoryImpl(coinApi)
        coinUseCase = CoinUseCase(coinRepositoryImpl)
    }

    @Test
    fun `test getCoinList success`() = runTest(testDispatcher) {
        val coinEth = Coin("ETH", "Ethereum", "ETH", 2, isNew = false, isActive = true, "crypto")
        val coinBtc = Coin("BTC", "Bitcoin", "BTC", 1, isNew = false, isActive = true, "crypto")
        val unSortedResult = listOf(coinEth, coinBtc)
        val sortedResult = listOf(coinBtc, coinEth)

        coEvery { coinRepositoryImpl.getCoins() } answers { unSortedResult }

        val results = mutableListOf<CoinListState>()
        val job = launch {
            viewModel.state.toList(results)
        }

        viewModel.getCoinList()

        advanceUntilIdle()

        assertEquals(CoinListState(isLoading = false), results[0])
        assertEquals(CoinListState(isLoading = true), results[1])
        assertEquals(
            CoinListState(
                isLoading = false,
                coins = sortedResult
            ), results[2]
        )
        job.cancel()
    }

    @Test
    fun `test getCoinList error`() = runTest(testDispatcher) {
        val errorMessage = "Failed to fetch coins"
        coEvery { coinRepositoryImpl.getCoins() } throws Exception(errorMessage)

        val results = mutableListOf<CoinListState>()

        val job = launch(testDispatcher) {
            viewModel.state.toList(results)
        }
        viewModel.getCoinList()

        advanceUntilIdle()
        assertEquals(CoinListState(isLoading = false), results[0])
        assertEquals(CoinListState(isLoading = true), results[1])
        assertEquals(
            CoinListState(error = DataState.Error(null, errorMessage)),
            results[2]
        )

        job.cancel()
    }
}