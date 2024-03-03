package com.example.easydictionary.data.translateRepository

import com.google.gson.annotations.SerializedName

data class TranslateResponse(
    @SerializedName("source-text") val sourceText: String,
    @SerializedName("destination-text") val translatedText: String
)

data class Translations(
    @SerializedName("all-translations") val allTranslations: List<List<String>>
)