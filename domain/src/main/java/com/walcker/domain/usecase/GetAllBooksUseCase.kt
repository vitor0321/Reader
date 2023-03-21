package com.walcker.domain.usecase

import com.walcker.domain.data.repository.BookRepositoryRemote
import com.walcker.domain.model.BookUI
import com.walcker.domain.usecase.base.CoroutinesDispatchers
import com.walcker.domain.usecase.base.ResultStatus
import com.walcker.domain.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
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