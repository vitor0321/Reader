package com.walcker.reader.view.utils.uiState

import com.walcker.reader.domain.model.BookUI

sealed class UiStateListBooks {
    object Loading : UiStateListBooks()
    data class SuccessAllBooks(val booksList: List<BookUI?>) : UiStateListBooks()
    data class Error(val error: Throwable) : UiStateListBooks()
}