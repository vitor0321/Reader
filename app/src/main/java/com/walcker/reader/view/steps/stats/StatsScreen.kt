package com.walcker.reader.view.steps.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.sharp.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.firebase.auth.FirebaseAuth
import com.walcker.reader.domain.model.BookUI
import com.walcker.reader.resource.LocalStrings
import com.walcker.reader.view.components.CardBookSearch
import com.walcker.reader.view.components.ErrorReader
import com.walcker.reader.view.components.Loading
import com.walcker.reader.view.components.TopBar
import com.walcker.reader.view.steps.home.HomeScreen
import com.walcker.reader.view.steps.home.HomeScreenViewModel
import com.walcker.reader.view.utils.Step
import com.walcker.reader.view.utils.uiState.UiStateListBooks

object StatsScreen : Step("stats_screen") {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel<HomeScreenViewModel>()
        val state by viewModel.state.collectAsState()
        StatsScreenObserver(state)
    }
}

@Composable
fun StatsScreenObserver(
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
    StatsScreenContent(
        loading = loading,
        error = error,
        listBookUI = listBookUI
    )
}

@Composable
fun StatsScreenContent(
    loading: MutableState<Boolean>,
    error: MutableState<Boolean>,
    listBookUI: SnapshotStateList<BookUI>,
) {

    val navigator = LocalNavigator.currentOrThrow
    val strings = LocalStrings.current.stats
    var books: List<BookUI> = emptyList()
    val currentUser = FirebaseAuth.getInstance().currentUser

    Scaffold(
        topBar = {
            TopBar(
                title = strings.title,
                icon = Icons.Default.ArrowBack,
                isHomeScreen = false
            ) {
                navigator.push(HomeScreen)
            }
        },
        backgroundColor = MaterialTheme.colors.primary
    ) { padding ->
        padding.calculateTopPadding()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            verticalArrangement = Arrangement.Top
        ) {

            when {
                loading.value -> Loading()
                error.value -> ErrorReader(message = "Humm ... something is wrong")
                listBookUI.isNotEmpty() -> {
                    books = listBookUI.filter {
                        it.userId == currentUser?.uid
                    }
                }
            }

            val readBooksList = books.filter {
                it.userId == currentUser?.uid && it.finishedReading != null
            }
            val readingBooksList = books.filter {
                it.userId == currentUser?.uid && it.startedReading != null
            }

            Row {
                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .padding(2.dp)
                ) {
                    Icon(imageVector = Icons.Sharp.Person, contentDescription = "icon")
                }
                Text(
                    text = "Hi, ${currentUser?.email.toString().split("@")[0].uppercase()}"
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                shape = CircleShape,
                elevation = 5.dp
            ) {
                Column(
                    modifier = Modifier.padding(start = 25.dp, top = 4.dp, bottom = 4.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = strings.yourStatus,
                        style = MaterialTheme.typography.h5
                    )
                    Divider()
                    Text(text = strings.booksReading(strings.reading, readingBooksList.size, strings.type))
                    Text(text = strings.booksReading(strings.read, readBooksList.size, strings.type))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Divider()

            if (books.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(items = readBooksList) {
                        CardBookSearch(it) {}
                    }
                }
            }
        }
    }
}
