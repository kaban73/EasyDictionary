package com.example.easydictionary.core

import androidx.lifecycle.ViewModel
import com.example.easydictionary.add.AddTranslateLiveDataWrapper
import com.example.easydictionary.add.AddViewModel
import com.example.easydictionary.data.roomRepository.Core
import com.example.easydictionary.data.roomRepository.Now
import com.example.easydictionary.data.roomRepository.RoomRepository
import com.example.easydictionary.data.translateRepository.TranslateRepository
import com.example.easydictionary.delete.DeleteLiveDataWrapper
import com.example.easydictionary.delete.DeleteViewModel
import com.example.easydictionary.list.ListViewModel
import com.example.easydictionary.main.MainViewModel
import com.example.easydictionary.main.Navigation

interface ProvideViewModel {
    fun <T : ViewModel> viewModel(viewModelClass : Class<T>) : T
    class Base(
        core : Core,
        private val clearViewModel: ClearViewModel
    ) : ProvideViewModel {
        private val navigation = Navigation.Base()
        private val roomRepository = RoomRepository.Base(core.dao(), Now.Base())
        private val translateRepository = TranslateRepository.Base(core.service())
        private val listLiveDataWrapper = ListLiveDataWrapper.Base()
        private val addTranslateLiveDataWrapper = AddTranslateLiveDataWrapper.Base()
        private val deleteLiveDataWrapper = DeleteLiveDataWrapper.Base()
        override fun <T : ViewModel> viewModel(viewModelClass: Class<T>): T = when(viewModelClass) {
            MainViewModel::class.java -> MainViewModel(navigation)
            ListViewModel::class.java -> ListViewModel(roomRepository,listLiveDataWrapper,navigation)
            AddViewModel::class.java -> AddViewModel(translateRepository, roomRepository, listLiveDataWrapper,addTranslateLiveDataWrapper, clearViewModel, navigation)
            DeleteViewModel::class.java -> DeleteViewModel(roomRepository,listLiveDataWrapper,deleteLiveDataWrapper,clearViewModel, navigation)
            else -> throw IllegalStateException("unknown viewModelClass $viewModelClass")
        } as T
    }
}