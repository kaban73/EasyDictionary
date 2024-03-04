package com.example.easydictionary.data.translateRepository

import com.example.easydictionary.add.AddTranslateLiveDataWrapper
import com.example.easydictionary.add.UiState
import com.example.easydictionary.core.ListLiveDataWrapper
import com.example.easydictionary.data.roomRepository.RoomRepository
import com.example.easydictionary.list.Translate

interface LoadResult {
    interface AddRepository {
        fun add(roomRepository: RoomRepository.Add) : Long
    }
    interface UpdateListLiveData {
        fun update(id : Long, updateLiveData : ListLiveDataWrapper.Add)
    }
    interface UpdateAddLiveData {
        fun update(errorLiveData: AddTranslateLiveDataWrapper.Update)
    }
    interface SuccessShow : AddRepository, UpdateListLiveData, LoadResult {
        fun success(roomRepository: RoomRepository.Add, updateListLiveData : ListLiveDataWrapper.Add, updateAddLiveData: AddTranslateLiveDataWrapper.Update)
    }
    interface ErrorShow : UpdateAddLiveData, LoadResult
    data class Success(private val data : TranslateResponse) : SuccessShow {
        override fun success(
            roomRepository: RoomRepository.Add,
            updateListLiveData: ListLiveDataWrapper.Add,
            updateAddLiveData: AddTranslateLiveDataWrapper.Update
        ) {
            val id = add(roomRepository)
            update(id, updateListLiveData)
            updateAddLiveData.update(UiState.ShowSuccess(SUCCESS))
        }

        override fun add(roomRepository: RoomRepository.Add): Long {
            return roomRepository.add(data)
        }

        override fun update(id : Long, updateLiveData: ListLiveDataWrapper.Add) {
            updateLiveData.add(Translate(id, data.sourceText, data.translatedText))
        }

    }
    data class Error(private val noConnection : Boolean) : ErrorShow {
        private val textError = if (noConnection) ERROR_NO_INTERNET else ERROR_OTHER
        override fun update(errorLiveData: AddTranslateLiveDataWrapper.Update) {
            errorLiveData.update(UiState.ShowError(textError))
        }
    }
    companion object {
        private const val ERROR_OTHER = "Something went wrong"
        private const val ERROR_NO_INTERNET = "No internet connection"
        private const val SUCCESS = "Successfully added"
    }
}
