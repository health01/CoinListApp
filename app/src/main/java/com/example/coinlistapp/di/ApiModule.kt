package com.example.coinlistapp.di

import com.example.coinlistapp.data.network.CoinAPiService
import com.example.coinlistapp.data.repository.CoinRepositoryImpl
import com.example.coinlistapp.repository.CoinRepository
import com.example.coinlistapp.util.Constants
import com.example.coinlistapp.util.DefaultCoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    private val commonRetrofitBuilder: Retrofit.Builder
        get() = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())

    @Provides
    @Singleton
    fun provideDefaultCoroutineDispatchers() = DefaultCoroutineDispatchers()

    @Provides
    @Singleton
    fun provideCoinApi(): CoinAPiService {
        return commonRetrofitBuilder
            .baseUrl(Constants.BASE_URL)
            .build().create(CoinAPiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(apiService: CoinAPiService): CoinRepository {
        return CoinRepositoryImpl(apiService)
    }
}