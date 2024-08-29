package com.example.coinlistapp.ui.coinDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinlistapp.data.DataState
import com.example.coinlistapp.domain.usecase.CoinUseCase
import com.example.coinlistapp.util.CoinDetail
import com.example.coinlistapp.util.DefaultCoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val coinUseCase: CoinUseCase,
    savedStateHandle: SavedStateHandle,
    private val dispatchers: DefaultCoroutineDispatchers,
) : ViewModel() {

    private val _state = MutableStateFlow(CoinDetailState())
    val state: StateFlow<CoinDetailState> = _state

    init {
        try {
            savedStateHandle.get<String>(CoinDetail.coinIdArg)?.let { coinId ->
                getCoinDetail(coinId)
            }

        } catch (throwable: Throwable) {
            _state.value = _state.value.copy(
                isLoading = false,
                error = DataState.Error(null, throwable.message)
            )
        }
    }

    fun getCoinDetail(coinId: String) {
        viewModelScope.launch(dispatchers.io()) {
            coinUseCase.getCoinById(coinId).collect { result ->
                when (result) {
                    is DataState.Loading -> {
                        _state.value = _state.value.copy(isLoading = true)
                    }

                    is DataState.Error -> {
                        _state.value = CoinDetailState(error = result)
                    }

                    is DataState.Success -> {
                        _state.value = _state.value.copy(isLoading = false, coin = result.data)
                    }
                }
            }
        }
    }
}