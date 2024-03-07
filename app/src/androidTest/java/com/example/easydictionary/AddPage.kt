package com.example.easydictionary

import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.android.material.textfield.TextInputEditText
import org.hamcrest.Matchers.allOf

class AddPage {
    private val rootId : Int = R.id.addLayout
    private fun title() = onView(
        allOf(
            withParent(isAssignableFrom(LinearLayout::class.java)),
            withParent(withId(rootId)),
            isAssignableFrom(TextView::class.java),
            withId(R.id.titleAddTextView),
            withText("Enter a text and select the language to translate:")
        )
    )

    fun checkVisibleNow() {
        title().check(matches(isDisplayed()))
    }
    fun checkNotVisibleNow() {
        title().check(doesNotExist())
    }
    fun inputText(text: String) {
        onView(
            allOf(
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(rootId)),
                isAssignableFrom(TextInputEditText::class.java),
                withId(R.id.translateInputEditText)
            )
        ).perform(typeText(text), closeSoftKeyboard())
    }
    fun clickSaveButton() {
        onView(
            allOf(
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(rootId)),
                isAssignableFrom(Button::class.java),
                withId(R.id.translateButton),
                withText("Translate")
            )
        ).perform(click())
    }
    fun chooseLanguage(radioButtonId : Int) {
        onView(
            allOf(
                withParent(isAssignableFrom(LinearLayout::class.java)),
                withParent(withId(rootId)),
                isAssignableFrom(RadioButton::class.java),
                withId(radioButtonId)
            )
        ).perform(click())
    }
}