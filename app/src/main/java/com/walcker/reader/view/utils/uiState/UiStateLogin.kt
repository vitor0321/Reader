package com.walcker.reader.view.utils.uiState

sealed class UiStateLogin {
    object Loading : UiStateLogin()
    object SuccessToLogin: UiStateLogin()
    object SuccessToHome: UiStateLogin()
    data class Error(val error: Exception) : UiStateLogin()
}