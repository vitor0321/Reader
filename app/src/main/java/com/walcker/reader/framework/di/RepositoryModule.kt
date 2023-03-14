package com.walcker.reader.framework.di

import com.walcker.core.data.repository.BookRepositoryRemote
import com.walcker.core.data.repository.FirebaseRepositoryRemote
import com.walcker.reader.framework.network.repository.BookRepositoryRemoteImpl
import com.walcker.reader.framework.network.repository.FirebaseRepositoryRemoteImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindCharacterRepository(repository: BookRepositoryRemoteImpl): BookRepositoryRemote

    @Singleton
    @Binds
    fun bindFirebaseRepositoryRemote(repository: FirebaseRepositoryRemoteImpl): FirebaseRepositoryRemote

}