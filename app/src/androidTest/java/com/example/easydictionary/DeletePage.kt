package com.example.easydictionary

import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.allOf
class DeletePage {
    private val rootId : Int = R.id.deleteLayout
    fun checkText(text : String) {
        onView(
            allOf(
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(rootId)),
                isAssignableFrom(TextView::class.java),
                withId(R.id.translateTextView)
            )
        ).check(matches(withText(text)))
    }
    fun checkVisibleNow() {
        onView(
            allOf(
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(rootId)),
                isAssignableFrom(TextView::class.java),
                withId(R.id.titleDeleteTextView),
                withText("Are you sure you want to delete translate?")
            )
        ).check(matches(isDisplayed()))
    }

    fun clickDeleteButton() {
        onView(
            allOf(
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(rootId)),
                isAssignableFrom(Button::class.java),
                withId(R.id.deleteButton),
                withText("Delete")
            )
        ).perform(click())
    }
}