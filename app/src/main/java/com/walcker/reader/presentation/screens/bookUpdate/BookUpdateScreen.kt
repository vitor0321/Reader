package com.walcker.reader.presentation.screens.bookUpdate

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.walcker.reader.presentation.components.ErrorReader
import com.walcker.reader.presentation.components.Loading
import com.walcker.reader.presentation.components.RatingBar
import com.walcker.reader.presentation.components.TopBar
import com.walcker.reader.presentation.screens.bookUpdate.areas.ButtonArea
import com.walcker.reader.presentation.screens.bookUpdate.areas.CardBookUpdate
import com.walcker.reader.presentation.screens.bookUpdate.areas.FormBookUpdate
import com.walcker.reader.presentation.screens.home.HomeScreen
import com.walcker.reader.presentation.screens.home.HomeScreenViewModel

class  BookUpdateScreen(val bookId: String) : Screen {

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            topBar = {
                TopBar(
                    title = "Update book",
                    icon = Icons.Default.ArrowBack,
                    isHomeScreen = false
                ) {
                    navigator.push(HomeScreen)
                }
            },
            backgroundColor = MaterialTheme.colors.primary
        ) {
            it.calculateTopPadding()
            UpdateScreenObserver(bookId = bookId)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun UpdateScreenObserver(
    bookId: String,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val currentUser = FirebaseAuth.getInstance().currentUser

    val bookUpDate = remember { mutableStateOf(BookUI()) }
    val loading = remember { mutableStateOf(false) }
    val error = remember { mutableStateOf(false) }

    viewModel.getAllBooksFromDatabase()

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
                        if (bookUI.userId == currentUser?.uid && bookUI.id == bookId) {
                            bookUpDate.value = bookUI
                        }
                    }
                }
                loading.value = false
                error.value = false
            }
            else -> {}
        }
    }

    UpdateScreenContent(loading = loading, error = error, bookUI = bookUpDate)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun UpdateScreenContent(
    loading: MutableState<Boolean>,
    error: MutableState<Boolean>,
    bookUI: MutableState<BookUI>,
) {

    when {
        error.value -> ErrorReader(message = "Sometime is Wrong when you update this book.. try again")
        loading.value -> Loading()
        bookUI.value.id != null -> {
            ShowUpdateView(bookUI = bookUI, loading = loading, error = error)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ShowUpdateView(
    bookUI: MutableState<BookUI>,
    loading: MutableState<Boolean>,
    error: MutableState<Boolean>
) {
    val notesText = remember { mutableStateOf("") }
    val ratingState = remember { mutableStateOf(0) }
    val isStartReading = remember { mutableStateOf(false) }
    val isFinishedReading = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        CardBookUpdate(bookUI = bookUI)

        FormBookUpdate(
            defaultValue = bookUI.value.notes ?: ""
        ) { note ->
            notesText.value = note
        }

        Text(
            text = "Rating",
            color = MaterialTheme.colors.secondary
        )

        Spacer(modifier = Modifier.height(8.dp))

        bookUI.value.rating?.let { ratingBook ->
            RatingBar(rating = ratingBook) { rating ->
                ratingState.value = rating
            }
        }

        Spacer(modifier = Modifier.height(20.dp))



        Spacer(modifier = Modifier.height(20.dp))

        ButtonArea(
            bookUI = bookUI,
            notesText = notesText,
            ratingState = ratingState,
            isStartReading = isStartReading,
            isFinishedReading = isFinishedReading,
            loading = loading,
            error = error
        )
    }
}
