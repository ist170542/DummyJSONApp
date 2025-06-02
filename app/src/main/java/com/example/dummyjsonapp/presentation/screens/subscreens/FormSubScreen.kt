package com.example.dummyjsonapp.presentation.screens.subscreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dummyjsonapp.R
import com.example.dummyjsonapp.presentation.viewmodels.Classification
import com.example.dummyjsonapp.presentation.viewmodels.FormViewModel
import com.example.dummyjsonapp.presentation.viewmodels.ValidatedField
import com.example.dummyjsonapp.presentation.viewmodels.ValidatedFormUIState
import com.example.dummyjsonapp.presentation.viewmodels.shouldShowError
import com.example.dummyjsonapp.util.AppConstants
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@Composable
fun ValidatedFormSubScreen(viewModel: FormViewModel) {
    val formState by viewModel.formState.collectAsState()

    ValidatedFormSubScreenContent(
        formUIState = formState,
        onNameChange = viewModel::updateName,
        onEmailChange = viewModel::updateEmail,
        onNumberChange = viewModel::updateNumber,
        onPromoCodeChange = viewModel::updatePromoCode,
        onClassificationChange = viewModel::updateClassification,
        onDateSelected = viewModel::updateDate
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ValidatedFormSubScreenContent(
    formUIState: ValidatedFormUIState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onNumberChange: (String) -> Unit,
    onPromoCodeChange: (String) -> Unit,
    onClassificationChange: (Classification) -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val showDatePicker = remember { mutableStateOf(false) }

    if (showDatePicker.value) {
        DatePickerDialog(
            onDateSelected = {
                onDateSelected(it)
                showDatePicker.value = false
            },
            onDismiss = { showDatePicker.value = false }
        )
    }

    var expanded by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    Scaffold(
//        //workaround, too much space added above the keyboard
//        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.form_title)) }
            )
        }
    ) { padding ->

        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { NameField(formUIState, onNameChange, focusManager) }
            item { EmailField(formUIState, onEmailChange, focusManager) }
            item { NumberField(formUIState, onNumberChange, focusManager) }
            item { PromoCodeField(formUIState, onPromoCodeChange, focusManager) }
            item {
                DateField(
                    formUIState = formUIState,
                    showDatePicker = showDatePicker,
                    dateFormatter = dateFormatter
                )
            }
            item {
                ClassificationField(
                    formUIState = formUIState,
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    onClassificationChange = onClassificationChange
                )
            }
            item {
                Button(
                    onClick = { },
                    enabled = formUIState.isValid,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        if (formUIState.isValid)
                            stringResource(R.string.form_valid_label)
                        else stringResource(R.string.form_invalid_label)
                    )
                }
            }
        }
    }

}

@Composable
fun NameField(state: ValidatedFormUIState, onValueChange: (String) -> Unit, focusManager: FocusManager) {
    OutlinedTextField(
        value = state.name.value,
        onValueChange = onValueChange,
        singleLine = true,
        isError = state.name.shouldShowError(),
        supportingText = {
            state.name.errorMessageRes?.let { Text(stringResource(it)) }
        },
        label = { Text(stringResource(R.string.form_name_label)) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun EmailField(state: ValidatedFormUIState, onValueChange: (String) -> Unit, focusManager: FocusManager) {
    OutlinedTextField(
        value = state.email.value,
        onValueChange = onValueChange,
        singleLine = true,
        isError = state.email.shouldShowError(),
        supportingText = {
            state.email.errorMessageRes?.let { Text(stringResource(it)) }
        },
        label = { Text(stringResource(R.string.form_email_label)) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun NumberField(state: ValidatedFormUIState, onValueChange: (String) -> Unit, focusManager: FocusManager) {
    OutlinedTextField(
        value = state.number.value,
        onValueChange = onValueChange,
        singleLine = true,
        isError = state.number.shouldShowError(),
        supportingText = {
            state.number.errorMessageRes?.let { Text(stringResource(it)) }
        },
        label = { Text(stringResource(R.string.form_number_label)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun PromoCodeField(state: ValidatedFormUIState, onValueChange: (String) -> Unit, focusManager: FocusManager) {
    OutlinedTextField(
        value = state.promoCode.value,
        onValueChange = onValueChange,
        singleLine = true,
        isError = state.promoCode.shouldShowError(),
        supportingText = {
            state.promoCode.errorMessageRes?.let { Text(stringResource(it)) }
        },
        label = { Text(stringResource(R.string.form_promo_code_label)) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(Modifier.height(12.dp))
}

@Composable
fun DateField(
    formUIState: ValidatedFormUIState,
    showDatePicker: MutableState<Boolean>,
    dateFormatter: DateTimeFormatter
) {
    Text(
        "${stringResource(R.string.form_delivery_date_label)}: " +
                (formUIState.deliveryDate.value?.format(dateFormatter)
                    ?: stringResource(R.string.form_select_date))
    )
    formUIState.deliveryDate.errorMessageRes?.let {
        Text(
            text = stringResource(it),
            color = MaterialTheme.colorScheme.error
        )
    }
    Button(onClick = { showDatePicker.value = true }) {
        Text(stringResource(R.string.form_select_date))
    }
    Spacer(Modifier.height(12.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassificationField(
    formUIState: ValidatedFormUIState,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onClassificationChange: (Classification) -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange
    ) {
        OutlinedTextField(
            value = formUIState.classification?.labelRes?.let { stringResource(it) } ?: AppConstants.EMPTY_STRING,
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.form_classification_label)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            formUIState.classificationOptions.forEach {
                DropdownMenuItem(
                    text = { Text(stringResource(it.labelRes)) },
                    onClick = {
                        onClassificationChange(it)
                        onExpandedChange(false)
                    }
                )
            }
        }
    }
    Spacer(Modifier.height(20.dp))
}

@Composable
fun DatePickerDialog(
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val today = Calendar.getInstance()

    val dialog = android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            onDateSelected(LocalDate.of(year, month + 1, dayOfMonth))
        },
        today.get(Calendar.YEAR),
        today.get(Calendar.MONTH),
        today.get(Calendar.DAY_OF_MONTH)
    )

    dialog.setOnDismissListener { onDismiss() }
    dialog.show()
}

@Preview(showBackground = true)
@Composable
fun ValidatedFormSubScreenContentPreview() {
    ValidatedFormSubScreenContent(
        formUIState = ValidatedFormUIState(
            name = ValidatedField("Ana"),
            email = ValidatedField("ana@exemplo.com"),
            number = ValidatedField("999123123"),
            promoCode = ValidatedField("TEST-001"),
            deliveryDate = ValidatedField(LocalDate.now()),
            classification = Classification.EXCELENTE,
            classificationOptions = Classification.entries.toList(),
            isValid = true
        ),
        onNameChange = {},
        onEmailChange = {},
        onNumberChange = {},
        onPromoCodeChange = {},
        onClassificationChange = {},
        onDateSelected = {}
    )
}
