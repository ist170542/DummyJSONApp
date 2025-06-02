package com.example.dummyjsonapp.util.formValidation

import com.example.dummyjsonapp.util.FormValidator
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDate

class FormValidatorTest {

    @Test
    fun `valid email passes validation`() {
        val errorRes = FormValidator.validateEmail("test@example.com").errorMessageRes
        assertNull(errorRes)
    }

    @Test
    fun `invalid email fails validation`() {
        val errorRes = FormValidator.validateEmail("invalid-email").errorMessageRes
        assertNotNull(errorRes)
    }

    @Test
    fun `valid promo code passes validation`() {
        val errorRes = FormValidator.validatePromoCode("VALID-C").errorMessageRes
        assertNull(errorRes)
    }

    @Test
    fun `promo code with lowercase or invalid characters fails validation`() {
        val errorRes = FormValidator.validatePromoCode("abc123").errorMessageRes
        assertNotNull(errorRes)
    }

    @Test
    fun `promo code with accents fails validation`() {
        val errorRes = FormValidator.validatePromoCode("ÁBC-DEF").errorMessageRes
        assertNotNull(errorRes)
    }

    @Test
    fun `future date fails validation`() {
        val futureDate = LocalDate.now().plusDays(1)
        val errorRes = FormValidator.validateDeliveryDate(futureDate, false).errorMessageRes
        assertNotNull(errorRes)
    }

    @Test
    fun `monday date fails validation`() {
        var monday = LocalDate.now()
        while (monday.dayOfWeek != DayOfWeek.MONDAY) {
            monday = monday.plusDays(1)
        }
        val errorRes = FormValidator.validateDeliveryDate(monday, false).errorMessageRes
        assertNotNull(errorRes)
    }

    @Test
    fun `valid past date that is not monday passes validation`() {
        var validDate = LocalDate.now().minusDays(1)
        while (validDate.dayOfWeek == DayOfWeek.MONDAY) {
            validDate = validDate.minusDays(1)
        }
        val errorRes = FormValidator.validateDeliveryDate(validDate, false).errorMessageRes
        assertNull(errorRes)
    }
}