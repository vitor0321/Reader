package com.walcker.reader.framework.di

import com.walcker.core.usecase.GetAllBooksFromDatabaseUseCase
import com.walcker.core.usecase.GetAllBooksFromDatabaseUseCaseImpl
import com.walcker.core.usecase.GetAllBooksUseCase
import com.walcker.core.usecase.GetAllBooksUseCaseImpl
import com.walcker.core.usecase.GetBookIdUseCase
import com.walcker.core.usecase.GetBookIdUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindGetAllBooksUseCase(useCase: GetAllBooksUseCaseImpl): GetAllBooksUseCase

    @Binds
    fun bindGetBookIdUseCase(useCase: GetBookIdUseCaseImpl): GetBookIdUseCase

    @Binds
    fun bindGetAllBooksFromDatabaseUseCase(useCase: GetAllBooksFromDatabaseUseCaseImpl): GetAllBooksFromDatabaseUseCase
}