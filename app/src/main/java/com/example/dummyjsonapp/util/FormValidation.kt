package com.example.dummyjsonapp.util

import com.example.dummyjsonapp.R
import com.example.dummyjsonapp.presentation.viewmodels.ValidatedField
import java.text.Normalizer
import java.time.DayOfWeek
import java.time.LocalDate

object FormValidator {

    private val emailRegex = Regex("^[A-Za-z](.*)(@)(.+)(\\.)(.+)$")
    private val promoCodeRegex = Regex("^[A-Z\\-]{3,7}$")

    fun validateRequiredField(value: String): ValidatedField<String> {
        val trimmed = value.trim()
        return ValidatedField(
            value = trimmed,
            isEdited = true,
            errorMessageRes = if (trimmed.isBlank()) R.string.error_required else null
        )
    }

    fun validateEmail(value: String): ValidatedField<String> {
        val trimmed = value.trim()
        val error = when {
            trimmed.isBlank() -> R.string.error_required
            !emailRegex.matches(trimmed) -> R.string.error_email_invalid
            else -> null
        }
        return ValidatedField(trimmed, isEdited = true, errorMessageRes = error)
    }

    fun validatePromoCode(code: String): ValidatedField<String> {
        val trimmed = code.trim().uppercase()
        val error = when {
            trimmed.isBlank() -> R.string.error_required
            !promoCodeRegex.matches(trimmed) -> R.string.error_promo_format
            containsAccents(trimmed) -> R.string.error_promo_accents
            else -> null
        }
        return ValidatedField(trimmed, isEdited = true, errorMessageRes = error)
    }

    fun validateDeliveryDate(date: LocalDate?, isEdited: Boolean): ValidatedField<LocalDate?> {
        val error = when {
            date == null -> R.string.error_required
            date.dayOfWeek == DayOfWeek.MONDAY -> R.string.error_monday_invalid
            date.isAfter(LocalDate.now()) -> R.string.error_future_date
            else -> null
        }
        return ValidatedField(date, isEdited = isEdited, errorMessageRes = error)
    }

    private fun containsAccents(input: String): Boolean {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
            .any { it in '\u0300'..'\u036f' }
    }
}