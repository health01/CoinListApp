package com.example.coinlistapp.data.network

import com.example.coinlistapp.data.DataState
import retrofit2.HttpException
import java.io.IOException

interface BasicApiCall {
    suspend fun <T> startApiCall(
        apiCall: suspend () -> T
    ): DataState<T> {
        return try {
            DataState.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    DataState.Error(
                        throwable.code(),
                        throwable.localizedMessage ?: "HttpException"
                    )
                }

                is IOException -> DataState.Error(400, "IOException")
                else -> DataState.Error(null, throwable.message)
            }
        }
    }
}