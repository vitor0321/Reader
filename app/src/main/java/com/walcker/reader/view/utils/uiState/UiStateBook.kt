package com.walcker.reader.view.utils.uiState

import com.walcker.reader.domain.model.BookUI

sealed class UiStateBook {
    object Loading : UiStateBook()
    data class Success(val booksList: BookUI) : UiStateBook()
    data class Error(val error: Throwable) : UiStateBook()
}