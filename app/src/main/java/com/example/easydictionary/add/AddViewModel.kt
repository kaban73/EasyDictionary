package com.example.easydictionary.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.easydictionary.core.ClearViewModel
import com.example.easydictionary.core.ListLiveDataWrapper
import com.example.easydictionary.data.roomRepository.RoomRepository
import com.example.easydictionary.data.translateRepository.LoadResult
import com.example.easydictionary.data.translateRepository.TranslateRepository
import com.example.easydictionary.main.Navigation
import com.example.easydictionary.main.Screen
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddViewModel(
    private val translateRepository: TranslateRepository,
    private val roomRepository: RoomRepository.Add,
    private val listLiveDataWrapper: ListLiveDataWrapper.Add,
    private val addTranslateLiveDataWrapper: AddTranslateLiveDataWrapper.Mutable,
    private val clear : ClearViewModel,
    private val navigation: Navigation.Update,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main
) : ViewModel(), AddTranslateLiveDataWrapper.Read{
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    override fun liveData(): LiveData<UiState> =
        addTranslateLiveDataWrapper.liveData()

    fun add(sourceLang : String, targetLang : String, text : String) {
        viewModelScope.launch(dispatcher) {
            val response = translateRepository.load(sourceLang, targetLang, text)
            when (response) {
                is LoadResult.Success -> {
                    val id = response.add(roomRepository)
                    withContext(dispatcherMain) {
                        response.update(id, listLiveDataWrapper)
                        response.update(addTranslateLiveDataWrapper)
                    }
                }
                is LoadResult.Error -> {
                    withContext(dispatcherMain) {
                        response.update(addTranslateLiveDataWrapper)
                    }
                }
            }
        }
    }

    fun comeback() {
        navigation.update(Screen.Pop)
        clear.clear(AddViewModel::class.java)
    }
}