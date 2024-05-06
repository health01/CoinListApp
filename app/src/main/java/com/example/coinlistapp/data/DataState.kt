package com.example.coinlistapp.data

sealed class DataState<out T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error(
        val code: Int?,
        val message: String?
    ) : DataState<Nothing>()

    data object Loading : DataState<Nothing>()
}