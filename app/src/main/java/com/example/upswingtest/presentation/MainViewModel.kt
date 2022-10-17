package com.example.upswingtest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upswingtest.data.model.User
import com.example.upswingtest.data.repository.IDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val dataRepository: IDataRepository) : ViewModel() {

    private var _name: String = "";
    private var _email: String = "";

    private val _dataStateFlow = MutableStateFlow<User>(User())
    val dataStateFlow = _dataStateFlow.asStateFlow()


    init {
        viewModelScope.launch {
            dataRepository.getData().collectLatest {
                _dataStateFlow.value = it
            }
        }
    }

    fun setName(name: String) {
        _name = name
    }

    fun setEmail(email: String) {
        _email = email
    }

    fun saveData() {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.saveData(User(_name, _email))
        }
    }

}