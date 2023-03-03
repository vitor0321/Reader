package com.walcker.core.usecase

import com.walcker.core.data.repository.BookRepositoryRemote
import com.walcker.core.model.BookUI
import com.walcker.core.usecase.base.CoroutinesDispatchers
import com.walcker.core.usecase.base.ResultStatus
import com.walcker.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetBookIdUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<BookUI>>

    data class Params(val searchQueryId: String)
}

class GetBookIdUseCaseImpl @Inject constructor(
    private val repository: BookRepositoryRemote,
    private val dispatchers: CoroutinesDispatchers
) : GetBookIdUseCase, UseCase<GetBookIdUseCase.Params, BookUI>() {

    override suspend fun doWork(
        params: GetBookIdUseCase.Params
    ): ResultStatus<BookUI> {
        return withContext(dispatchers.io()) {
            val data = repository.getBooksId(params.searchQueryId)
            ResultStatus.Success(data)
        }
    }
}