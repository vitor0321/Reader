package com.walcker.reader.view.steps.detatils

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.walcker.reader.domain.BookRepositoryRemote
import com.walcker.reader.view.utils.uiState.UiStateBook
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getBookIdUseCase: BookRepositoryRemote,
) : StateScreenModel<UiStateBook>(UiStateBook.Loading) {

    fun getBookIdInfo(bookId: String) {
        coroutineScope.launch {
            try {
                mutableState.value = UiStateBook.Success(getBookIdUseCase.getBooksId(bookId))
            } catch (error: Throwable) {
                mutableState.value = UiStateBook.Error(error)
            }
        }
    }
}