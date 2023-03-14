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

    suspend fun invoke(params: Params): ResultStatus<BookUI>

    data class Params(val searchQueryId: String)
}

class GetBookIdUseCaseImpl @Inject constructor(
    private val repository: BookRepositoryRemote,
    private val dispatchers: CoroutinesDispatchers
) : GetBookIdUseCase {

    override suspend fun invoke(params: GetBookIdUseCase.Params): ResultStatus<BookUI> {
        return withContext(dispatchers.io()) {
            try {
                ResultStatus.Loading
                val data = repository.getBooksId(params.searchQueryId)
                ResultStatus.Success(data)
            } catch (t: Throwable) {
                ResultStatus.Error(t)
            }
        }
    }
}