package com.example.coinlistapp.ui.coinList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinlistapp.data.DataState
import com.example.coinlistapp.domain.usecase.CoinUseCase
import com.example.coinlistapp.util.DefaultCoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val coinUseCase: CoinUseCase,
    private val dispatchers: DefaultCoroutineDispatchers,
) : ViewModel() {

    private val _state = MutableStateFlow(CoinListState())
    val state: StateFlow<CoinListState> = _state

    init {
        getCoinList()
    }

    fun getCoinList() {
        viewModelScope.launch(dispatchers.main()) {

            coinUseCase.getCoinList().collect { result ->
                when (result) {
                    is DataState.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }

                    is DataState.Error -> {
                        _state.value = CoinListState(error = result)
                    }

                    is DataState.Success -> {
                        _state.value = CoinListState(coins = result.data)
                    }
                }
            }
        }
    }
}