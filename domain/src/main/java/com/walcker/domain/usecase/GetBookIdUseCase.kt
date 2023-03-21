package com.walcker.domain.usecase

import com.walcker.domain.data.repository.BookRepositoryRemote
import com.walcker.domain.model.BookUI
import com.walcker.domain.usecase.base.CoroutinesDispatchers
import com.walcker.domain.usecase.base.ResultStatus
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