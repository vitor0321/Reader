package com.walcker.reader.presentation.screens.stats

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.sharp.Person
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
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.firebase.auth.FirebaseAuth
import com.walcker.core.model.BookUI
import com.walcker.reader.presentation.components.CardBook
import com.walcker.reader.presentation.components.CardBookSearch
import com.walcker.reader.presentation.components.ErrorReader
import com.walcker.reader.presentation.components.Loading
import com.walcker.reader.presentation.components.TopBar
import com.walcker.reader.presentation.screens.home.HomeScreen
import com.walcker.reader.presentation.screens.home.HomeScreenViewModel

object StatsScreen : Screen {

    @Composable
    override fun Content() {
        StatsScreenObserver()
    }
}

@Composable
fun StatsScreenObserver(
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
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
                listBookUI.clear()
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
    listBookUI: SnapshotStateList<BookUI>
) {

    val navigator = LocalNavigator.currentOrThrow
    var books: List<BookUI> = emptyList()
    val currentUser = FirebaseAuth.getInstance().currentUser

    Scaffold(
        topBar = {
            TopBar(
                title = "Book Stats",
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
                        text = "Your Stats",
                        style = MaterialTheme.typography.h5
                    )
                    Divider()
                    Text(text = "You're reading: ${readingBooksList.size} books")
                    Text(text = "You've read: ${readBooksList.size} books")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Divider()

            if (books.isNotEmpty()){
                LazyColumn(modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
                ){
                    items(items = readBooksList){
                        CardBookSearch(it){}
                    }
                }
            }
        }
    }
}
