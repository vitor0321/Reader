package com.walcker.reader.di

import com.walcker.domain.data.repository.BookRepositoryRemote
import com.walcker.domain.data.repository.FirebaseRepositoryRemote
import com.walcker.reader.data.network.repository.BookRepositoryRemoteImpl
import com.walcker.reader.data.network.repository.FirebaseRepositoryRemoteImpl
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