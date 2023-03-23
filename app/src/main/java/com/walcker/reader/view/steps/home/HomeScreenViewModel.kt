package com.walcker.reader.view.steps.home

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.walcker.reader.domain.FirebaseRepositoryRemote
import com.walcker.reader.view.utils.uiState.UiStateListBooks
import kotlinx.coroutines.launch

internal class HomeScreenViewModel(
    private val firebaseRepositoryRemote: FirebaseRepositoryRemote,
) : StateScreenModel<UiStateListBooks>(UiStateListBooks.Loading) {

    init {
        getAllBooksFromDatabase()
    }

    private fun getAllBooksFromDatabase() {
        coroutineScope.launch {
            try {
                mutableState.value = UiStateListBooks.SuccessAllBooks(
                    booksList = firebaseRepositoryRemote.getAllBooksFromDatabase()
                )
            }catch (error: Throwable){
                mutableState.value = UiStateListBooks.Error(error)
            }
        }
    }
}