package com.walcker.reader.di

import com.walcker.domain.usecase.GetAllBooksFromDatabaseUseCase
import com.walcker.domain.usecase.GetAllBooksFromDatabaseUseCaseImpl
import com.walcker.domain.usecase.GetAllBooksUseCase
import com.walcker.domain.usecase.GetAllBooksUseCaseImpl
import com.walcker.domain.usecase.GetBookIdUseCase
import com.walcker.domain.usecase.GetBookIdUseCaseImpl
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