package com.example.easydictionary

import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.hamcrest.CoreMatchers.allOf

class ListPage {
    private val rootId : Int = R.id.listLayout
    private fun recyclerViewMatcher() = RecyclerViewMatcher(R.id.recyclerView)

    private fun title() = onView(
        allOf(
            withText("Your translates:"),
            isAssignableFrom(TextView::class.java),
            withId(R.id.listTextView),
            withParent(isAssignableFrom(ConstraintLayout::class.java)),
            withParent(withId(rootId))
        )
    )

    fun checkVisibleNow() {
        title().check(matches(isDisplayed()))
    }

    fun clickAddButton() {
        onView(
            allOf(
                withId(R.id.addButton),
                isAssignableFrom(FloatingActionButton::class.java),
                withParent(isAssignableFrom(ConstraintLayout::class.java)),
                withParent(withId(rootId))
            )
        ).perform(click())
    }

    fun checkNotVisibleNow() {
        title().check(doesNotExist())
    }

    fun checkTranslate(position : Int, translate : String) {
        onView(
            allOf(
                isAssignableFrom(TextView::class.java),
                withId(R.id.elementTextView),
                recyclerViewMatcher().atPosition(position,R.id.elementTextView)
            )
        ).check(matches(withText(translate)))
    }


    fun clickTranslateAt(position: Int) {
        onView(recyclerViewMatcher().atPosition(position)).perform(click())
    }
}