package com.example.dummyjsonapp.util.textNormalizer

import com.example.dummyjsonapp.util.TextNormalizer
import org.junit.Assert.assertEquals
import org.junit.Test

class TextNormalizerTest {

    @Test
    fun `removes accents from string`() {
        val input = "Máscara"
        val expected = "mascara"

        val result = TextNormalizer.normalizeText(input)
        assertEquals(expected, result)
    }

    @Test
    fun `converts to lowercase`() {
        val input = "LÁPIS"
        val expected = "lapis"

        val result = TextNormalizer.normalizeText(input)
        assertEquals(expected, result)
    }

    @Test
    fun `removes multiple accents and spaces`() {
        val input = "RÉSOLUTIÓN   TESTÉ"
        val expected = "resolution   teste"

        val result = TextNormalizer.normalizeText(input)
        assertEquals(expected, result)
    }

    @Test
    fun `non-accented text remains unchanged`() {
        val input = "keyboard"
        val result = TextNormalizer.normalizeText(input)
        assertEquals("keyboard", result)
    }
}