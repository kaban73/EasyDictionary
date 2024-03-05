package com.example.easydictionary.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.easydictionary.core.ListLiveDataWrapper
import com.example.easydictionary.data.roomRepository.RoomRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListViewModel(
    private val repository: RoomRepository.Read,
    private val liveDataWrapper: ListLiveDataWrapper.Mutable,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherMain : CoroutineDispatcher = Dispatchers.Main
) : ViewModel(), ListLiveDataWrapper.Read {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    override fun liveData(): LiveData<List<TranslateUi>> = liveDataWrapper.liveData()

    fun init() {
        viewModelScope.launch(dispatcher) {
            val list = repository.list().map {
                TranslateUi(it.id,it.sourceText,it.translatedText)
            }
            withContext(dispatcherMain) {
                liveDataWrapper.update(list)
            }
        }
    }
}