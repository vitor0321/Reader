package com.walcker.reader.di

import com.walcker.reader.domain.BookRepositoryRemote
import com.walcker.reader.domain.FirebaseRepositoryRemote
import com.walcker.reader.view.steps.detatils.DetailsViewModel
import com.walcker.reader.view.steps.home.HomeScreenViewModel
import com.walcker.reader.view.steps.login.LoginScreenViewModel
import com.walcker.reader.view.steps.search.SearchViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val viewModelModule = DI.Module(name = "viewModelModule") {

    bindProvider { DetailsViewModel(instance<BookRepositoryRemote>()) }
    bindProvider { HomeScreenViewModel(instance<FirebaseRepositoryRemote>()) }
    bindProvider { LoginScreenViewModel() }
    bindProvider { SearchViewModel(instance<BookRepositoryRemote>()) }
}
