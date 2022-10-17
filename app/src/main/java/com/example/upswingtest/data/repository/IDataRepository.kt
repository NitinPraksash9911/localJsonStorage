package com.example.upswingtest.data.repository

import com.example.upswingtest.data.model.User
import kotlinx.coroutines.flow.Flow

interface IDataRepository {
    suspend fun saveData(user: User)
    suspend fun getData(): Flow<User>
}