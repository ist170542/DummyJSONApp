package com.example.dummyjsonapp.presentation.viewmodels

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import com.example.dummyjsonapp.R
import com.example.dummyjsonapp.util.FormValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

data class ValidatedFormUIState(
    val name: String = "",
    val email: String = "",
    val number: String = "",
    val promoCode: String = "",
    val deliveryDate: LocalDate? = null,
    val classificationOptions: List<Classification> = emptyList(),
    val classification: Classification? = null,
    val isValid: Boolean = false
)

/**
 * ViewModel responsible for managing the state and validation logic
 * of the validated form screen.
 *
 * It exposes a [StateFlow] of [ValidatedFormUIState], handles updates to
 * individual fields, and automatically evaluates whether the form is valid
 * based on business rules.
 *
 */
class FormViewModel : ViewModel() {

    private val classificationOptions = Classification.entries

    private val _formState = MutableStateFlow(ValidatedFormUIState(
        classificationOptions = classificationOptions
    ))

    val formState: StateFlow<ValidatedFormUIState> = _formState.asStateFlow()

    fun updateName(name: String) = update { it.copy(name = name) }
    fun updateEmail(email: String) = update { it.copy(email = email) }
    fun updateNumber(number: String) {
        if (number.all(Char::isDigit)) update { it.copy(number = number) }
    }

    fun updatePromoCode(code: String) = update { it.copy(promoCode = code.uppercase()) }
    fun updateDate(date: LocalDate) = update { it.copy(deliveryDate = date) }
    fun updateClassification(classification: Classification) = update { it.copy(classification = classification) }

    private fun update(transform: (ValidatedFormUIState) -> ValidatedFormUIState) {
        _formState.update { current ->
            val updated = transform(current)
            updated.copy(isValid = validateForm(updated))
        }
    }

    private fun validateForm(state: ValidatedFormUIState): Boolean {
        return state.name.isNotBlank() &&
                state.email.isNotBlank() &&
                state.number.isNotBlank() &&
                state.promoCode.isNotBlank() &&
                state.deliveryDate != null &&
                state.classification != null &&
                FormValidator.isEmailValid(state.email) &&
                FormValidator.isPromoCodeValid(state.promoCode) &&
                FormValidator.isDateValid(state.deliveryDate)
    }

}

enum class Classification(@StringRes val labelRes: Int) {
    MAU(R.string.classification_mau),
    SATISFATORIO(R.string.classification_satisfatorio),
    BOM(R.string.classification_bom),
    MUITO_BOM(R.string.classification_muito_bom),
    EXCELENTE(R.string.classification_excelente)
}