package com.walcker.reader.view.steps.home

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.firebase.auth.FirebaseAuth
import com.itau.search.view.SearchActivity
import com.walcker.reader.domain.model.BookUI
import com.walcker.reader.resource.LocalStrings
import com.walcker.reader.view.components.FloatButton
import com.walcker.reader.view.steps.home.areas.TitleSection
import com.walcker.reader.view.components.TopBar
import com.walcker.reader.view.steps.bookUpdate.BookUpdateScreen
import com.walcker.reader.view.steps.home.areas.BookListArea
import com.walcker.reader.view.steps.home.areas.ReadingRightNowArea
import com.walcker.reader.view.steps.home.areas.TopOfHomeArea
import com.walcker.reader.view.steps.search.SearchScreen
import com.walcker.reader.view.utils.Step
import com.walcker.reader.view.utils.uiState.UiStateListBooks

internal object HomeScreen : Step("home_screen") {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel<HomeScreenViewModel>()
        val state by viewModel.state.collectAsState()

        HomeObserver(state = state)
    }
}

@Composable
private fun HomeObserver(
    state: UiStateListBooks,
) {
    val listBookUI = remember { mutableStateListOf<BookUI>() }
    val loading = remember { mutableStateOf(false) }
    val error = remember { mutableStateOf(false) }

    val currentUser = FirebaseAuth.getInstance().currentUser

    when (state) {
        is UiStateListBooks.Loading -> {
            loading.value = true
            error.value = false
        }
        is UiStateListBooks.Error -> {
            loading.value = false
            error.value = true
        }
        is UiStateListBooks.SuccessAllBooks -> {
            listBookUI.clear()
            state.booksList.map { book ->
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

    HomeContent(loading = loading, error = error, listBookUI = listBookUI)
}

@Composable
private fun HomeContent(
    loading: MutableState<Boolean>,
    error: MutableState<Boolean>,
    listBookUI: SnapshotStateList<BookUI>,
) {
    val navigator = LocalNavigator.currentOrThrow
    val strings = LocalStrings.current
    val context = LocalContext.current

    Scaffold(
        topBar = { TopBar(title = strings.home.title) },
        backgroundColor = MaterialTheme.colors.primary,
        floatingActionButton = {
            Row {
                FloatButton(icon = Icons.Default.Add) {
                    startActivity(context, Intent(context, SearchActivity::class.java), null)
                    //navigator.push(SearchScreen)
                }
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
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {

                TopOfHomeArea(
                    label = if (listBookUI.isEmpty())
                        "hummm ... is so empty" else "Your reading \n" + "activity right now..."
                )

                ReadingRightNowArea(books = listBookUI, loading = loading, error = error) { bookId ->
                    navigator.push(BookUpdateScreen(bookId = bookId))
                }

                TitleSection(label = if (listBookUI.isEmpty()) "You need search a new book" else "Reading List")

                BookListArea(listOfBooks = listBookUI, loading = loading, error = error) { bookId ->
                    navigator.push(BookUpdateScreen(bookId = bookId))
                }
            }
        }
    }
}

