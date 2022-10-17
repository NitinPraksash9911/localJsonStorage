package com.example.upswingtest.data.repository

import android.content.Context
import com.example.upswingtest.data.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

class DataRepository @Inject constructor(@ApplicationContext private val context: Context) :
    IDataRepository {
    private var file: File = File(context.filesDir, "data.json")

    init {
        if (!file.exists()) {
            file.mkdir()
        }
    }

    override suspend fun saveData(name: String, email: String) {
        val jsonObject = JSONObject()
        jsonObject.put("name", name)
        jsonObject.put("email", email)
        file.writeText(jsonObject.toString())
    }

    override suspend fun getData(): Flow<User> {
        return flow {
            file.bufferedReader().useLines {
            }
        }
    }
}