package com.walcker.core.usecase

import com.walcker.core.data.repository.BookRepositoryRemote
import com.walcker.core.model.BookUI
import com.walcker.core.usecase.base.CoroutinesDispatchers
import com.walcker.core.usecase.base.ResultStatus
import com.walcker.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetAllBooksUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<List<BookUI>>>

    data class Params(val searchQuery: String)
}

class GetAllBooksUseCaseImpl @Inject constructor(
    private val repository: BookRepositoryRemote,
    private val dispatchers: CoroutinesDispatchers
) : GetAllBooksUseCase, UseCase<GetAllBooksUseCase.Params, List<BookUI>>() {

    override suspend fun doWork(
        params: GetAllBooksUseCase.Params
    ): ResultStatus<List<BookUI>> {

            val data = repository.getBooks(params.searchQuery)
          return  ResultStatus.Success(data)

    }
}