package com.example.easydictionary.data.translateRepository

import com.example.easydictionary.add.AddTranslateLiveDataWrapper
import com.example.easydictionary.add.UiState
import com.example.easydictionary.core.ListLiveDataWrapper
import com.example.easydictionary.data.roomRepository.RoomRepository
import com.example.easydictionary.list.TranslateUi

interface LoadResult {
    interface AddRepository {
        fun add(roomRepository: RoomRepository.Add) : Long
    }
    interface UpdateListLiveData {
         fun update(id : Long, updateLiveData : ListLiveDataWrapper.Add)
    }
    interface UpdateAddLiveData {
        fun update(updateAddLiveData: AddTranslateLiveDataWrapper.Update)
    }
    interface SuccessShow : AddRepository, UpdateListLiveData, UpdateAddLiveData, LoadResult
    interface ErrorShow : UpdateAddLiveData, LoadResult
    data class Success(private val data : TranslateResponse) : SuccessShow {
        override fun add(roomRepository: RoomRepository.Add): Long {
            return roomRepository.add(data)
        }
        override fun update(id : Long, updateLiveData: ListLiveDataWrapper.Add) {
            updateLiveData.add(TranslateUi(id, data.sourceText, data.translatedText))
        }
        override fun update(updateAddLiveData: AddTranslateLiveDataWrapper.Update) {
            updateAddLiveData.update(UiState.ShowSuccess(SUCCESS))
        }

    }
    data class Error(private val noConnection : Boolean) : ErrorShow {
        private val textError = if (noConnection) ERROR_NO_INTERNET else ERROR_OTHER
        override fun update(updateAddLiveData: AddTranslateLiveDataWrapper.Update) {
            updateAddLiveData.update(UiState.ShowError(textError))
        }
    }
    companion object {
        private const val ERROR_OTHER = "Something went wrong"
        private const val ERROR_NO_INTERNET = "No internet connection"
        private const val SUCCESS = "Successfully added"
    }
}
