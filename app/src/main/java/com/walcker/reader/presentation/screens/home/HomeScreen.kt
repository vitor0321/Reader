package com.walcker.reader.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.firebase.auth.FirebaseAuth
import com.walcker.core.model.BookUI
import com.walcker.reader.presentation.components.ErrorReader
import com.walcker.reader.presentation.components.FloatButton
import com.walcker.reader.presentation.components.Loading
import com.walcker.reader.presentation.components.TitleSection
import com.walcker.reader.presentation.components.TopBar
import com.walcker.reader.presentation.screens.bookUpdate.BookUpdateScreen
import com.walcker.reader.presentation.screens.home.areas.BookListArea
import com.walcker.reader.presentation.screens.home.areas.ReadingRightNowArea
import com.walcker.reader.presentation.screens.home.areas.TopOfHomeArea
import com.walcker.reader.presentation.screens.search.SearchScreen

object HomeScreen : Screen {

    @Composable
    override fun Content() {
        HomeObserver()
    }
}

@Composable
fun HomeObserver(viewModel: HomeScreenViewModel = hiltViewModel()) {

    val listBookUI = remember { mutableStateListOf<BookUI>() }
    val loading = remember { mutableStateOf(false) }
    val error = remember { mutableStateOf(false) }

    viewModel.getAllBooksFromDatabase()

    val lifecycleOwner = LocalLifecycleOwner.current
    val currentUser = FirebaseAuth.getInstance().currentUser

    viewModel.state.observe(lifecycleOwner) { uiState ->
        when (uiState) {
            is HomeScreenViewModel.UiState.Loading -> {
                loading.value = true
                error.value = false
            }
            is HomeScreenViewModel.UiState.Error -> {
                loading.value = false
                error.value = true
            }
            is HomeScreenViewModel.UiState.SuccessAllBooks -> {
                uiState.booksList.map { book ->
                    book?.let { bookUI ->
                        if (bookUI.userId == currentUser?.uid) {
                            listBookUI.add(bookUI)
                        }
                    }
                }
                loading.value = false
                error.value = false
            }
        }
    }

    HomeContent(loading = loading, error = error, listBookUI = listBookUI)
}

@Composable
private fun HomeContent(
    loading: MutableState<Boolean>,
    error: MutableState<Boolean>,
    listBookUI: SnapshotStateList<BookUI>
) {
    val navigator = LocalNavigator.currentOrThrow

    Scaffold(
        topBar = { TopBar(title = "A.Reader") },
        backgroundColor = MaterialTheme.colors.primary,
        floatingActionButton = {
            FloatButton {
                navigator.push(SearchScreen)
            }
        }
    ) {
        it.calculateTopPadding()

        Surface(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxSize(),
            color = MaterialTheme.colors.primary
        ) {

            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center
            ) {

                TopOfHomeArea()

                ReadingRightNowArea(books = listBookUI, loading = loading, error = error) { bookId ->
                    navigator.push(BookUpdateScreen(bookId = bookId))
                }

                TitleSection(label = "Reading List")

                BookListArea(listOfBooks = listBookUI,loading = loading, error = error) { bookId ->
                    navigator.push(BookUpdateScreen(bookId = bookId))
                }
            }
        }
    }
}

