package com.app.currencyconverter.application.di

import com.app.currencyconverter.data.ApiInterface
import com.app.currencyconverter.data.CurrencyRemoteDataSource
import com.app.currencyconverter.data.ICurrencyRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun provideCurrencyDataSource(apiInterface: ApiInterface) : ICurrencyRemoteDataSource {
        return CurrencyRemoteDataSource(
            apiInterface = apiInterface
        )
    }
}