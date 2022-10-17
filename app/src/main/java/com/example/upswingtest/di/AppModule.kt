package com.example.upswingtest.di

import com.example.upswingtest.data.repository.DataRepository
import com.example.upswingtest.data.repository.IDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {
    @Binds
    @Singleton
    fun bindDataRepository(dataRepository: DataRepository): IDataRepository
}