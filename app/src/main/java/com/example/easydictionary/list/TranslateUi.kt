package com.example.easydictionary.list

import android.widget.TextView
import com.example.easydictionary.delete.DeleteTranslateUi

data class TranslateUi(
    val id : Long,
    val sourceText : String,
    val translatedText : String
) {
    fun areItemsTheSame(other : TranslateUi) = id ==other.id
    fun areContentsTheSame(other: TranslateUi) = sourceText == other.sourceText && translatedText == other.translatedText
    fun delete(deleteTranslateUi: DeleteTranslateUi) = deleteTranslateUi.delete(id)
    fun show(textView: TextView) {
        textView.text = "$sourceText - $translatedText"
    }
}