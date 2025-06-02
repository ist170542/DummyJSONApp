package com.example.dummyjsonapp.util

import java.text.Normalizer
import java.util.Locale

object TextNormalizer {

    /**
     * Normalize search string to remove diacritics and convert to lowercase
     * e.g. "Máscara" -> "mascara"
     */
    fun normalizeText(input: String): String {
        return Normalizer
            .normalize(input.lowercase(Locale.getDefault()), Normalizer.Form.NFD)
            .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
    }

}