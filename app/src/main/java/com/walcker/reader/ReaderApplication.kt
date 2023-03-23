package com.walcker.reader

import android.app.Application
import com.walcker.reader.di.coroutinesModule
import com.walcker.reader.di.firebaseModule
import com.walcker.reader.di.networkModule
import com.walcker.reader.di.repositoryModule
import com.walcker.reader.di.viewModelModule
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.bind
import org.kodein.di.conf.ConfigurableDI
import org.kodein.di.provider

class ReaderApplication: Application(), DIAware {

    private val appModule = DI.Module(name = "application") {
        bind<Application>() with provider {
            this@ReaderApplication
        }
    }

    override val di = ConfigurableDI(mutable = true).apply {
        addImport(appModule)
        loadAppModules()
    }

}


internal fun ConfigurableDI.loadAppModules() {
    addImport(coroutinesModule)
    addImport(firebaseModule)
    addImport(networkModule)
    addImport(repositoryModule)
    addImport(viewModelModule)
}