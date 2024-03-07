package com.example.easydictionary

import androidx.test.espresso.Espresso
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.easydictionary.main.MainActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EasyDictionaryTest {
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private val mainPage = MainPage()
    private val listPage = ListPage()
    private val addPage = AddPage()
    private val deletePage = DeletePage()

    @Test
    fun test_add_success() {
        mainPage.checkVisibleNow()

        listPage.checkVisibleNow()
        listPage.clickAddButton()
        listPage.checkNotVisibleNow()

        addPage.checkVisibleNow()
        addPage.inputText("hello")
        addPage.chooseLanguage(R.id.ruLangRadioButton)
        addPage.clickSaveButton()
        addPage.checkInputText()
        addPage.checkStatus("Successfully added")
        Espresso.pressBack()
        addPage.checkNotVisibleNow()

        listPage.checkVisibleNow()
        listPage.checkTranslate(position = 0, translate = "hello - привет")
    }

    @Test
    fun test_add_error() {
        mainPage.checkVisibleNow()

        listPage.checkVisibleNow()
        listPage.clickAddButton()
        listPage.checkNotVisibleNow()

        addPage.checkVisibleNow()
        addPage.inputText("")
        addPage.chooseLanguage(R.id.ruLangRadioButton)
        addPage.clickSaveButton()
        addPage.checkInputText()
        addPage.checkStatus("Something went wrong")

    }

    @Test
    fun test_delete_translate() {
        mainPage.checkVisibleNow()

        listPage.checkVisibleNow()
        listPage.clickAddButton()
        listPage.checkNotVisibleNow()

        addPage.checkVisibleNow()
        addPage.inputText("hello")
        addPage.chooseLanguage(R.id.ruLangRadioButton)
        addPage.clickSaveButton()
        addPage.checkInputText()
        addPage.checkStatus("Successfully added")

        addPage.inputText("пока")
        addPage.chooseLanguage(R.id.enLangRadioButton)
        addPage.clickSaveButton()
        addPage.checkInputText()
        addPage.checkStatus("Successfully added")

        Espresso.pressBack()
        addPage.checkNotVisibleNow()

        listPage.checkVisibleNow()
        listPage.checkTranslate(position = 0, translate = "hello - привет")
        listPage.checkTranslate(position = 1, translate = "пока - Bye")

        listPage.clickTranslateAt(0)
        listPage.checkNotVisibleNow()

        deletePage.checkVisibleNow()
        deletePage.checkText("hello - привет")
        deletePage.clickDeleteButton()
        deletePage.checkNotVisibleNow()

        listPage.checkVisibleNow()
        listPage.checkTranslate(position = 0, translate = "пока - Bye")
    }

    @Test
    fun test_recreate() {
        mainPage.checkVisibleNow()

        listPage.checkVisibleNow()
        listPage.clickAddButton()
        listPage.checkNotVisibleNow()

        addPage.checkVisibleNow()
        addPage.inputText("hello")
        activityScenarioRule.scenario.recreate()
        addPage.chooseLanguage(R.id.ruLangRadioButton)
        addPage.clickSaveButton()
        addPage.checkInputText()
        addPage.checkStatus("Successfully added")

        addPage.inputText("пока")
        addPage.chooseLanguage(R.id.enLangRadioButton)
        activityScenarioRule.scenario.recreate()
        addPage.clickSaveButton()
        addPage.checkInputText()
        addPage.checkStatus("Successfully added")

        Espresso.pressBack()
        addPage.checkNotVisibleNow()

        listPage.checkVisibleNow()
        listPage.checkTranslate(position = 0, translate = "hello - привет")
        listPage.checkTranslate(position = 1, translate = "пока - Bye")
        activityScenarioRule.scenario.recreate()
        listPage.checkTranslate(position = 0, translate = "hello - привет")
        listPage.checkTranslate(position = 1, translate = "пока - Bye")

        listPage.clickTranslateAt(0)
        listPage.checkNotVisibleNow()

        deletePage.checkVisibleNow()
        deletePage.checkText("hello - привет")
        activityScenarioRule.scenario.recreate()
        deletePage.checkText("hello - привет")
        deletePage.clickDeleteButton()
        deletePage.checkNotVisibleNow()

        listPage.checkVisibleNow()
        listPage.checkTranslate(position = 0, translate = "пока - Bye")
        activityScenarioRule.scenario.recreate()
        listPage.checkTranslate(position = 0, translate = "пока - Bye")
    }

    @Test
    fun test_add_comeback() {
        mainPage.checkVisibleNow()

        listPage.checkVisibleNow()
        listPage.clickAddButton()
        listPage.checkNotVisibleNow()

        addPage.checkVisibleNow()
        addPage.inputText("hello")
        addPage.chooseLanguage(R.id.ruLangRadioButton)
        addPage.clickSaveButton()
        addPage.checkInputText()
        addPage.checkStatus("Successfully added")
        Espresso.pressBack()
        addPage.checkNotVisibleNow()

        listPage.checkVisibleNow()
        listPage.checkTranslate(position = 0, translate = "hello - привет")
        listPage.clickAddButton()
        listPage.checkNotVisibleNow()

        addPage.checkVisibleNow()
        Espresso.pressBack()
        addPage.checkNotVisibleNow()

        listPage.checkVisibleNow()
        listPage.checkTranslate(position = 0, translate = "hello - привет")
    }

    @Test
    fun test_delete_comeback() {
        mainPage.checkVisibleNow()

        listPage.checkVisibleNow()
        listPage.clickAddButton()
        listPage.checkNotVisibleNow()

        addPage.checkVisibleNow()
        addPage.inputText("hello")
        addPage.chooseLanguage(R.id.ruLangRadioButton)
        addPage.clickSaveButton()
        addPage.checkInputText()
        addPage.checkStatus("Successfully added")

        addPage.inputText("пока")
        addPage.chooseLanguage(R.id.enLangRadioButton)
        addPage.clickSaveButton()
        addPage.checkInputText()
        addPage.checkStatus("Successfully added")

        Espresso.pressBack()
        addPage.checkNotVisibleNow()

        listPage.checkVisibleNow()
        listPage.checkTranslate(position = 0, translate = "hello - привет")
        listPage.checkTranslate(position = 1, translate = "пока - Bye")

        listPage.clickTranslateAt(0)
        listPage.checkNotVisibleNow()

        deletePage.checkVisibleNow()
        deletePage.checkText("hello - привет")
        Espresso.pressBack()
        deletePage.checkNotVisibleNow()

        listPage.checkVisibleNow()
        listPage.checkTranslate(position = 0, translate = "hello - привет")
        listPage.checkTranslate(position = 1, translate = "пока - Bye")
    }
}