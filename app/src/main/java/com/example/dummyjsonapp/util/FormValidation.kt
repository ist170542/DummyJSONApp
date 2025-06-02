package com.example.dummyjsonapp.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.Normalizer
import java.time.DayOfWeek
import java.time.LocalDate

object FormValidator {
    private val emailRegex = Regex("^[A-Za-z](.*)(@)(.+)(\\.)(.+)")
    private val promoCodeRegex = Regex("^[A-Z\\-]{3,7}$")

    fun isEmailValid(email: String): Boolean = emailRegex.matches(email)

    fun isPromoCodeValid(code: String): Boolean =
        promoCodeRegex.matches(code) && !containsAccents(code)

    fun isDateValid(date: LocalDate?): Boolean {
        val today = LocalDate.now()
        return date != null && date.dayOfWeek != DayOfWeek.MONDAY && !date.isAfter(today)
    }

    private fun containsAccents(input: String): Boolean {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
            .any { it in '\u0300'..'\u036f' }
    }
}