package com.walcker.reader.presentation.screens.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.walcker.core.model.BookUI
import com.walcker.core.usecase.GetAllBooksUseCase
import com.walcker.core.usecase.GetBookIdUseCase
import com.walcker.core.usecase.base.CoroutinesDispatchers
import com.walcker.reader.presentation.common.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope.coroutineContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getBookIdUseCase: GetBookIdUseCase,
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
                                emit(UiState.Error)
                            }
                        )
                }

                is Action.GetBookOfId -> {
                    getBookIdUseCase.invoke(GetBookIdUseCase.Params(it.queryBookId))
                        .watchStatus(
                            loading = {
                                emit(UiState.Loading)
                            },
                            success = {
                                emit(UiState.SuccessBookId(it))
                            },
                            error = {
                                emit(UiState.Error)
                            }
                        )
                }
            }
        }
    }


    fun getAllBooks(queryBook: String) {
        action.value = Action.GetAllBooks(queryBook = queryBook)
    }

    fun getBookId(queryBookId: String) {
        action.value = Action.GetBookOfId(queryBookId = queryBookId)
    }

    sealed class UiState {
        object Loading : UiState()
        data class SuccessAllBooks(val booksList: List<BookUI>) : UiState()
        data class SuccessBookId(val booksListId: BookUI) : UiState()
        object Error : UiState()
    }

    sealed class Action {
        data class GetAllBooks(val queryBook: String) : Action()
        data class GetBookOfId(val queryBookId: String) : Action()
    }
}