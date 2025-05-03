package com.app.currencyconverter.application.di

import com.app.currencyconverter.data.CurrencyRepository
import com.app.currencyconverter.data.ICurrencyRemoteDataSource
import com.app.currencyconverter.data.RateHistoryRepository
import com.app.currencyconverter.domain.repository.ICurrencyRepository
import com.app.currencyconverter.domain.repository.IRateHistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideCurrencyRepository(remoteDataSource: ICurrencyRemoteDataSource) : ICurrencyRepository {
        return CurrencyRepository(remoteDataSource)
    }
    
    @Singleton
    @Provides
    fun provideRateHistoryRepository(remoteDataSource: ICurrencyRemoteDataSource) : IRateHistoryRepository {
        return RateHistoryRepository(remoteDataSource)
    }
}