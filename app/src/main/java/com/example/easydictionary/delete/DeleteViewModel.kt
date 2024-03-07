package com.example.easydictionary.delete

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.easydictionary.core.ClearViewModel
import com.example.easydictionary.core.ListLiveDataWrapper
import com.example.easydictionary.data.roomRepository.RoomRepository
import com.example.easydictionary.list.TranslateUi
import com.example.easydictionary.main.Navigation
import com.example.easydictionary.main.Screen
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeleteViewModel(
    private val repository: RoomRepository.Delete,
    private val listLiveDataWrapper: ListLiveDataWrapper.Delete,
    private val deleteLiveDataWrapper: DeleteLiveDataWrapper.Mutable,
    private val clear : ClearViewModel,
    private val navigation: Navigation.Update,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain: CoroutineDispatcher = Dispatchers.Main
) : ViewModel(), DeleteLiveDataWrapper.Read {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun init(itemId : Long) {
        viewModelScope.launch(dispatcher) {
            val pair = Pair(repository.item(itemId).sourceText, repository.item(itemId).translatedText)
            withContext(dispatcherMain) {
                deleteLiveDataWrapper.update(pair)
            }
        }
    }

    fun delete(itemId: Long) {
        viewModelScope.launch(dispatcher) {
            val translate = repository.item(itemId).let {
                TranslateUi(it.id, it.sourceText, it.translatedText)
            }
            repository.delete(itemId)
            withContext(dispatcherMain) {
                listLiveDataWrapper.delete(translate)
                comeback()
            }
        }
    }

    fun comeback() {
        clear.clear(DeleteViewModel::class.java)
        navigation.update(Screen.Pop)
    }

    override fun liveData(): LiveData<Pair<String, String>> = deleteLiveDataWrapper.liveData()

}