package com.example.easydictionary.list

import com.example.easydictionary.data.roomRepository.RoomRepository
import com.example.easydictionary.main.FakeNavigation
import kotlinx.coroutines.Dispatchers
import org.junit.Test
class ListViewModelTest {
    @Test
    fun test() {
        val repository = FakeRoomRepository.Base()
        val liveDataWrapper = FakeListLiveDataWrapper.Base()
        val navigation = FakeNavigation.Base()
        val viewModel = ListViewModel(
            repository = repository,
            liveDataWrapper = liveDataWrapper,
            navigation = navigation,
            dispatcher = Dispatchers.Unconfined,
            dispatcherMain = Dispatchers.Unconfined
        )

        repository.expectedList(
            listOf(
                Translate(
                    id = 0L,
                    sourceText = "hello",
                    translatedText = "Привет"
                ),
                Translate(
                    id = 0L,
                    sourceText = "bye",
                    translatedText = "Пока"
                )
            )
        )

        viewModel.init()

        liveDataWrapper.checkUpdateCalls(
            listOf(
                TranslateUi(
                    id = 0L,
                    sourceText = "hello",
                    translatedText = "Привет"
                ),
                TranslateUi(
                    id = 0L,
                    sourceText = "bye",
                    translatedText = "Пока"
                )
            )
        )
    }

    private interface FakeRoomRepository : RoomRepository.Read {
        fun expectedList(list : List<Translate>)

        class Base : FakeRoomRepository {
            private val list = mutableListOf<Translate>()
            override fun expectedList(list : List<Translate>) {
                this.list.addAll(list)
            }

            override fun list(): List<Translate> {
                return list
            }

        }
    }
}