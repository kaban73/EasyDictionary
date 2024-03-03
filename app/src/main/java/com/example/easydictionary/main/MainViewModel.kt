package com.example.easydictionary.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.easydictionary.list.ListScreen

class MainViewModel(
    private val navigation: Navigation.Mutable
) : ViewModel(), Navigation.Read{
    fun init(firstRun : Boolean) {
        if (firstRun) navigation.update(ListScreen)
    }

    override fun liveData(): LiveData<Screen> =
        navigation.liveData()
}