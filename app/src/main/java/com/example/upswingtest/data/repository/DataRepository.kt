package com.example.upswingtest.data.repository

import android.content.Context
import android.util.Log
import com.example.upswingtest.data.Constant
import com.example.upswingtest.data.model.User
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import java.io.File
import java.io.IOException
import javax.inject.Inject

class DataRepository @Inject constructor(@ApplicationContext private val context: Context) :
    IDataRepository {
    private var file: File = File(context.filesDir, Constant.DATA_FILE_NAME)

    init {
        if (!file.exists()) {
            file.createNewFile()
        }
    }

    override suspend fun saveData(user: User) {
        val jsonObject = JSONObject()
        jsonObject.put("name", user.name)
        jsonObject.put("email", user.email)
        context.openFileOutput(Constant.DATA_FILE_NAME, Context.MODE_PRIVATE).use {
            it.write(jsonObject.toString().toByteArray())
        }
    }

    override suspend fun getData(): Flow<User> {
        return flow {
            try {
                val user =
                    context.openFileInput(Constant.DATA_FILE_NAME).bufferedReader().use { data ->
                        data.readText()
                    }

                if (user.isNullOrBlank()) return@flow

                emit(Gson().fromJson(user, User::class.java))

            } catch (io: IOException) {
                Log.d("DataRepository", "no file present")
                emit(User())
            }
        }
    }
}