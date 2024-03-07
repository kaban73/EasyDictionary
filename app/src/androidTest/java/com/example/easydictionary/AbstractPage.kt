package com.example.easydictionary

import android.widget.FrameLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.hamcrest.Matchers.allOf

class MainPage {
    private val rootId : Int = R.id.container
    fun checkVisibleNow() {
        onView(
            allOf(
                isAssignableFrom(FrameLayout::class.java),
                withId(rootId)
            )
        ).check(matches(isDisplayed()))
    }
    fun checkNotVisibleNow() {
        onView(
            allOf(
                isAssignableFrom(FrameLayout::class.java),
                withId(rootId)
            )
        ).check(doesNotExist())
    }
}