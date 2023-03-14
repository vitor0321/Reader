package com.walcker.reader.presentation.screens.bookUpdate.areas

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import com.walcker.reader.presentation.components.InputField

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FormBookUpdate(
    defaultValue: String,
    onSearch: (String) -> Unit
) {
    val cleanNotes = HtmlCompat.fromHtml(defaultValue, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    val textFieldValue = rememberSaveable { mutableStateOf(cleanNotes) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(textFieldValue.value) { textFieldValue.value.trim().isNotEmpty() }

    InputField(
        modifier = Modifier
            .height(140.dp)
            .padding(3.dp)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        valueState = textFieldValue,
        labelId = "Enter your thoughts",
        enabled = true,
        onAction = KeyboardActions {
            if (!valid) return@KeyboardActions
            onSearch(textFieldValue.value.trim())
            keyboardController?.hide()
        }
    )
}
