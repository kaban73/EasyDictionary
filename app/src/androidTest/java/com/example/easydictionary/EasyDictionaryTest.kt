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

    private val listPage = ListPage()
    private val addPage = AddPage()
    private val deletePage = DeletePage()

    @Test
    fun test_add_success() {
        listPage.checkVisibleNow()
        listPage.clickAddButton()
    }
}