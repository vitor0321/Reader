package com.walcker.reader.framework.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.walcker.core.usecase.base.AppCoroutinesDispatchers
import com.walcker.core.usecase.base.CoroutinesDispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface CoroutinesModule {

    @Singleton
    @Binds
    fun bindDispatcher(dispatchers: AppCoroutinesDispatchers): CoroutinesDispatchers
}