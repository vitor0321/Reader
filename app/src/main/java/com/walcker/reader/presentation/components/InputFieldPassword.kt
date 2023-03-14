package com.walcker.reader.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputFieldPassword(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean = true,
    isSingleLine: Boolean = true,
    passwordVisibility: MutableState<Boolean>,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Password,
    onAction: KeyboardActions = KeyboardActions.Default
) {

    val visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(
        modifier = modifier
            .background(Color.Transparent)
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text = labelId) },
        singleLine = isSingleLine,
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colors.onBackground,
        ),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.secondary,
            unfocusedBorderColor = MaterialTheme.colors.onBackground,
            cursorColor = MaterialTheme.colors.secondary,
            focusedLabelColor = MaterialTheme.colors.secondary,
            unfocusedLabelColor = MaterialTheme.colors.onBackground,
        ),
        trailingIcon = {
            PasswordVisibility(passwordVisibility = passwordVisibility)
        },
    )
}

@Composable
private fun PasswordVisibility(
    passwordVisibility: MutableState<Boolean>
) {

    val visible = passwordVisibility.value

    IconButton(onClick = {
        passwordVisibility.value = !visible
    }) {
        Icons.Default.Close
    }
}
