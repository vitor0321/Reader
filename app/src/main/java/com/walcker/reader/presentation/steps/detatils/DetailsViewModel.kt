package com.walcker.reader.presentation.steps.detatils

import androidx.lifecycle.ViewModel
import com.walcker.domain.model.BookUI
import com.walcker.domain.usecase.GetBookIdUseCase
import com.walcker.domain.usecase.base.ResultStatus
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