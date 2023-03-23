package com.walcker.reader.di

import com.walcker.reader.data.network.BookRepositoryRemoteImpl
import com.walcker.reader.data.network.FirebaseRepositoryRemoteImpl
import com.walcker.reader.domain.BookRepositoryRemote
import com.walcker.reader.domain.FirebaseRepositoryRemote
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val repositoryModule = DI.Module("repositoryModule") {

    bind<BookRepositoryRemote>() with singleton {
        BookRepositoryRemoteImpl(instance())
    }

    bind<FirebaseRepositoryRemote>() with singleton {
        FirebaseRepositoryRemoteImpl(instance())
    }
}