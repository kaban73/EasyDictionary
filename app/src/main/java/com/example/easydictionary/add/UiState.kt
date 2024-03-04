package com.example.easydictionary.add

import android.graphics.Color
import android.view.View
import android.widget.TextView
import java.io.Serializable

interface UiState : Serializable {
    fun apply(textView: TextView)
    data class ShowSuccess(private val text : String) : UiState {
        override fun apply(textView: TextView) {
            textView.visibility = View.VISIBLE
            textView.setTextColor(Color.GREEN)
            textView.text = text
        }

    }
    data class ShowError(private val text : String) : UiState {
        override fun apply(textView: TextView) {
            textView.visibility = View.VISIBLE
            textView.setTextColor(Color.RED)
            textView.text = text
        }

    }
}