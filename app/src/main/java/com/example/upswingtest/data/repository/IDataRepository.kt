package com.example.upswingtest.data.repository

import com.example.upswingtest.data.model.User
import kotlinx.coroutines.flow.Flow

interface IDataRepository {
    suspend fun saveData(name: String, email:String)
    suspend fun getData(): Flow<User>
}