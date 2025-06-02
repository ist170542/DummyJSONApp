package com.example.dummyjsonapp.util.formValidation

import com.example.dummyjsonapp.util.FormValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDate

class FormValidatorTest {
    @Test
    fun `valid email passes validation`() {
        assertTrue(FormValidator.isEmailValid("test@example.com"))
    }

    @Test
    fun `invalid email fails validation`() {
        assertFalse(FormValidator.isEmailValid("invalid-email"))
    }

    @Test
    fun `valid promo code passes validation`() {
        assertTrue(FormValidator.isPromoCodeValid("TEST-"))
    }

    @Test
    fun `invalid promo code fails validation`() {
        assertFalse(FormValidator.isPromoCodeValid("test123"))
    }

    @Test
    fun `future date fails validation`() {
        assertFalse(FormValidator.isDateValid(LocalDate.now().plusDays(1)))
    }

    @Test
    fun `past non-Monday date passes validation`() {
        val date = LocalDate.now().minusDays(1)
        if (date.dayOfWeek != DayOfWeek.MONDAY) {
            assertTrue(FormValidator.isDateValid(date))
        }
    }

    @Test
    fun `monday date fails validation`() {
        // Find next Monday in the past or today
        var date = LocalDate.now()
        while (date.dayOfWeek != DayOfWeek.MONDAY) {
            date = date.minusDays(1)
        }
        assertFalse(FormValidator.isDateValid(date))
    }
}