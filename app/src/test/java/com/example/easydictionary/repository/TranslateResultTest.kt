package com.example.easydictionary.repository

import com.example.easydictionary.add.AddTranslateLiveDataWrapper
import com.example.easydictionary.add.FakeAddTranslateLiveDataWrapper
import com.example.easydictionary.add.UiState
import com.example.easydictionary.core.ListLiveDataWrapper
import com.example.easydictionary.data.roomRepository.RoomRepository
import com.example.easydictionary.data.translateRepository.LoadResult
import com.example.easydictionary.data.translateRepository.TranslateResponse
import com.example.easydictionary.list.FakeListLiveDataWrapper
import com.example.easydictionary.list.Translate
import org.junit.Assert.assertEquals
import org.junit.Test

class TranslateResultTest {
    @Test
    fun test_success() {
        val result = LoadResult.Success(data = TranslateResponse(
                sourceText = "hello",
                translatedText = "привет"
            )
        )
        val addLiveDataWrapper = FakeAddTranslateLiveDataWrapper.Base()
        val addLiveDataWrapperUpdate : AddTranslateLiveDataWrapper.Update = addLiveDataWrapper
        val listLiveDataWrapper = FakeListLiveDataWrapper.Base()
        val listLiveDataWrapperUpdate : ListLiveDataWrapper.Add = listLiveDataWrapper
        val roomRepository = FakeRoomRepository.Base()
        val roomRepositoryAdd : RoomRepository.Add = roomRepository
        result.success(
            roomRepository = roomRepositoryAdd,
            updateListLiveData = listLiveDataWrapperUpdate,
            updateAddLiveData = addLiveDataWrapperUpdate
        )
        roomRepository.checkAddCalls(listOf(
            Translate(
                id = 0L,
                sourceText = "hello",
                translatedText = "привет"
            )
        ))
        listLiveDataWrapper.checkUpdateCalls(listOf(
            Translate(
                id = 0L,
                sourceText = "hello",
                translatedText = "привет"
            )
        ))
        addLiveDataWrapper.checkUpdateCalls(listOf(
            UiState.ShowSuccess(text = "Successfully added")
        ))
    }

    @Test
    fun test_no_connection() {
        val result = LoadResult.Error(noConnection = true)
        val errorLiveDataWrapper = FakeAddTranslateLiveDataWrapper.Base()
        val errorLiveDataWrapperUpdate : AddTranslateLiveDataWrapper.Update = errorLiveDataWrapper
        result.update(errorLiveData = errorLiveDataWrapperUpdate)
        errorLiveDataWrapper.checkUpdateCalls(listOf(
            UiState.ShowError(text = "No internet connection")
        ))
    }

    @Test
    fun test_other() {
        val result = LoadResult.Error(noConnection = false)
        val errorLiveDataWrapper = FakeAddTranslateLiveDataWrapper.Base()
        val errorLiveDataWrapperUpdate : AddTranslateLiveDataWrapper.Update = errorLiveDataWrapper
        result.update(errorLiveData = errorLiveDataWrapperUpdate)
        errorLiveDataWrapper.checkUpdateCalls(listOf(
            UiState.ShowError(text = "Something went wrong")
        ))
    }

    private interface FakeRoomRepository : RoomRepository.Mutable {
        fun checkAddCalls(expected : List<Translate>)

        class Base : FakeRoomRepository {
            private val actualAddList = mutableListOf<Translate>()
            private var id = 0L
            override fun checkAddCalls(expected: List<Translate>) {
                assertEquals(expected, actualAddList)
            }

            override fun list(): List<Translate> {
                throw IllegalStateException("not used in test")
            }

            override fun add(value: TranslateResponse): Long {
                actualAddList.add(Translate(id, value.sourceText, value.translatedText))
                return id++
            }

        }
    }
}