package com.walcker.reader.presentation.steps.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.walcker.domain.model.BookUI
import com.walcker.domain.usecase.GetAllBooksUseCase
import com.walcker.domain.usecase.base.CoroutinesDispatchers
import com.walcker.reader.presentation.common.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getAllBooksUseCase: GetAllBooksUseCase,
    private val dispatchers: CoroutinesDispatchers
) : ViewModel() {

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action.switchMap {
        liveData(dispatchers.main()) {
            when (it) {
                is Action.GetAllBooks -> {
                    getAllBooksUseCase.invoke(GetAllBooksUseCase.Params(it.queryBook))
                        .watchStatus(
                            loading = {
                                emit(UiState.Loading)
                            },
                            success = {
                                emit(UiState.SuccessAllBooks(it))
                            },
                            error = {
                                emit(UiState.Error(it))
                            }
                        )
                }
            }
        }
    }


    fun getAllBooks(queryBook: String) {
        action.value = Action.GetAllBooks(queryBook = queryBook)
    }

    sealed class UiState {
        object Loading : UiState()
        data class SuccessAllBooks(val booksList: List<BookUI>) : UiState()
        data class Error(val error: Throwable) : UiState()
    }

    sealed class Action {
        data class GetAllBooks(val queryBook: String) : Action()
    }
}