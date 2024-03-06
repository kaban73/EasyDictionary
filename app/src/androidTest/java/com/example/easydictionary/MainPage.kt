package com.example.easydictionary

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.hamcrest.CoreMatchers.allOf

class MainPage{
    fun clickAddButton() {
        onView(
            allOf(
                withId(R.id.addButton),
                isAssignableFrom(FloatingActionButton::class.java),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withParent(withId(R.id.rootLayout))
            )
        )
    }
    fun checkItem(position : Int, text : String) {
        onView(
            RecyclerViewMatcher(R.id.recyclerView).atPosition(
                position,
                R.id.elementTextView
            )
        ).check(matches(withText(text)))
    }

    fun clickItem(position : Int) {
        onView(
            RecyclerViewMatcher(R.id.recyclerView).atPosition(
                position
            )
        ).perform(click())
    }
}