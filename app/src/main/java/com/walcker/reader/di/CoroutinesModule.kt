package com.walcker.reader.di

import com.walcker.reader.domain.utils.AppCoroutinesDispatchers
import com.walcker.reader.domain.utils.CoroutinesDispatchers
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val coroutinesModule = DI.Module("coroutinesModule") {

    bind<CoroutinesDispatchers>() with singleton {
        AppCoroutinesDispatchers()
    }
}