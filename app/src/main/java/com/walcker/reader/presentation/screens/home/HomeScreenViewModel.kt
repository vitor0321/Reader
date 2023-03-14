package com.walcker.reader.presentation.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.walcker.core.model.BookUI
import com.walcker.core.usecase.GetAllBooksFromDatabaseUseCase
import com.walcker.core.usecase.base.CoroutinesDispatchers
import com.walcker.reader.presentation.common.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getAllBooksFromDatabaseUseCase: GetAllBooksFromDatabaseUseCase,
    private val dispatchers: CoroutinesDispatchers
) : ViewModel() {

    private val action = MutableLiveData<Action>()
    val state: LiveData<UiState> = action.switchMap {
        liveData(dispatchers.main()) {
            when (it) {
                is Action.GetAllBooks -> {
                    getAllBooksFromDatabaseUseCase(GetAllBooksFromDatabaseUseCase.Params)
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

    fun getAllBooksFromDatabase() {
        action.value = Action.GetAllBooks
    }

    sealed class UiState {
        object Loading : UiState()
        data class SuccessAllBooks(val booksList: List<BookUI?>) : UiState()
        data class Error(val error: Throwable) : UiState()
    }

    sealed class Action {
        object GetAllBooks : Action()
    }
}