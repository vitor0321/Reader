package com.walcker.reader.presentation.screens.detatils

import androidx.lifecycle.ViewModel
import com.walcker.core.model.BookUI
import com.walcker.core.usecase.GetBookIdUseCase
import com.walcker.core.usecase.base.ResultStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getBookIdUseCase: GetBookIdUseCase,
): ViewModel() {


    suspend fun getBookIdInfo(bookId: String): ResultStatus<BookUI> {
        return getBookIdUseCase.invoke(GetBookIdUseCase.Params(bookId))
    }
}