package com.walcker.reader.view.steps.search

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.walcker.reader.domain.BookRepositoryRemote
import com.walcker.reader.domain.model.BookUI
import com.walcker.reader.domain.utils.CoroutinesDispatchers
import com.walcker.reader.view.utils.uiState.UiStateListBooks
import kotlinx.coroutines.launch

internal class SearchViewModel(
    private val bookRepositoryRemote: BookRepositoryRemote,
) : StateScreenModel<UiStateListBooks>(UiStateListBooks.SuccessAllBooks(listOf())) {

    fun getAllBooks(queryBook: String) = coroutineScope.launch {
        mutableState.value = UiStateListBooks.Loading
        try {
            mutableState.value = UiStateListBooks.SuccessAllBooks(
                bookRepositoryRemote.getBooks(queryBook)
            )
        } catch (error: Throwable) {
            mutableState.value = UiStateListBooks.Error(error)
        }
    }
}