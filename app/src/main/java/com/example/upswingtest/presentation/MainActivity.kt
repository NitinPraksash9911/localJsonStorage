package com.example.upswingtest.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.upswingtest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dataViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        dataSaving()
        dataObserver()
    }

    private fun dataObserver() {
        lifecycleScope.launchWhenCreated {
            dataViewModel.dataStateFlow.collectLatest {
                binding.nameEt.setText(it.name)
                binding.emailEt.setText(it.email)
            }
        }
    }

    private fun init() {
        dataViewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    private fun dataSaving() {
        binding.nameEt.addTextChangedListener {
            dataViewModel.setName(it.toString())
        }
        binding.emailEt.addTextChangedListener {
            dataViewModel.setEmail(it.toString())
        }

    }

    override fun onPause() {
        super.onPause()
        dataViewModel.saveData()
    }


}