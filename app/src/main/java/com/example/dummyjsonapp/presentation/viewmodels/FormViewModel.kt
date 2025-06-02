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
    val name: ValidatedField<String> = ValidatedField(""),
    val email: ValidatedField<String> = ValidatedField(""),
    val number: ValidatedField<String> = ValidatedField(""),
    val promoCode: ValidatedField<String> = ValidatedField(""),
    val deliveryDate: ValidatedField<LocalDate?> = ValidatedField(null),
    val classificationOptions: List<Classification> = emptyList(),
    val classification: Classification? = null,
    val isValid: Boolean = false
)

// Represents a field with validation state
data class ValidatedField<T>(
    val value: T,
    val isEdited: Boolean = false,
    @StringRes val errorMessageRes: Int? = null
)

fun ValidatedField<*>.shouldShowError(): Boolean =
    isEdited && errorMessageRes != null

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

    fun updateName(name: String) = update {
        it.copy(
            name = FormValidator.validateRequiredField(name)
        )
    }

    fun updateEmail(email: String) = update {
        it.copy(
            email = FormValidator.validateEmail(email)
        )
    }

    fun updateNumber(number: String) {
        if (number.all(Char::isDigit)) {
            update {
                it.copy(
                    number = FormValidator.validateRequiredField(number)
                )
            }
        }
    }


    fun updatePromoCode(code: String) = update {
        val normalized = code.uppercase()
        it.copy(
            promoCode = FormValidator.validatePromoCode(normalized)
        )
    }

    fun updateDate(date: LocalDate) = update {
        it.copy(deliveryDate = FormValidator.validateDeliveryDate(date, isEdited = true))
    }

    fun updateClassification(classification: Classification) = update {
        it.copy(classification = classification)
    }

    private fun update(transform: (ValidatedFormUIState) -> ValidatedFormUIState) {
        _formState.update { current ->
            val updated = transform(current)
            updated.copy(isValid = validateForm(updated))
        }
    }

    private fun validateForm(state: ValidatedFormUIState): Boolean {
        return state.name.errorMessageRes == null &&
                state.email.errorMessageRes == null &&
                state.number.errorMessageRes == null &&
                state.promoCode.errorMessageRes == null &&
                state.deliveryDate.errorMessageRes == null &&
                state.classification != null
    }

}

enum class Classification(@StringRes val labelRes: Int) {
    MAU(R.string.classification_mau),
    SATISFATORIO(R.string.classification_satisfatorio),
    BOM(R.string.classification_bom),
    MUITO_BOM(R.string.classification_muito_bom),
    EXCELENTE(R.string.classification_excelente)
}

