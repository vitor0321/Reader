package com.walcker.domain.usecase

import com.walcker.domain.data.repository.FirebaseRepositoryRemote
import com.walcker.domain.model.BookUI
import com.walcker.domain.usecase.base.CoroutinesDispatchers
import com.walcker.domain.usecase.base.ResultStatus
import com.walcker.domain.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetAllBooksFromDatabaseUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<List<BookUI?>>>

    object Params
}

class GetAllBooksFromDatabaseUseCaseImpl @Inject constructor(
    private val repository: FirebaseRepositoryRemote,
    private val dispatchers: CoroutinesDispatchers
) : GetAllBooksFromDatabaseUseCase, UseCase<GetAllBooksFromDatabaseUseCase.Params, List<BookUI?>>() {

    override suspend fun doWork(
        params: GetAllBooksFromDatabaseUseCase.Params
    ): ResultStatus<List<BookUI?>> {
        return withContext(dispatchers.io()) {
            val data = repository.getAllBooksFromDatabase()
            ResultStatus.Success(data)
        }
    }
}