package com.example.easydictionary.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.example.easydictionary.core.App
import com.example.easydictionary.core.ProvideViewModel
import com.example.easydictionary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ProvideViewModel {
    private lateinit var b : ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        viewModel = viewModel(MainViewModel::class.java)
        viewModel.liveData().observe(this) {
            it.show(supportFragmentManager, b.container.id)
        }
        viewModel.init(savedInstanceState == null)
    }

    override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T =
        (application as App).viewModel(viewModelClass)
}