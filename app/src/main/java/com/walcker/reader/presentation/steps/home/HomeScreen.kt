package com.walcker.reader.presentation.steps.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.google.firebase.auth.FirebaseAuth
import com.walcker.domain.model.BookUI
import com.walcker.reader.presentation.common.Step
import com.walcker.reader.presentation.components.FloatButton
import com.walcker.reader.presentation.components.TitleSection
import com.walcker.reader.presentation.components.TopBar
import com.walcker.reader.presentation.steps.bookUpdate.BookUpdateScreen
import com.walcker.reader.presentation.steps.home.areas.BookListArea
import com.walcker.reader.presentation.steps.home.areas.ReadingRightNowArea
import com.walcker.reader.presentation.steps.home.areas.TopOfHomeArea
import com.walcker.reader.presentation.steps.openbanking.OpenbankingScreen
import com.walcker.reader.presentation.steps.search.SearchScreen

object HomeScreen : Step("home_screen") {

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

    HomeContent(loading = loading, error = error, listBookUI = listBookUI)
}

@Composable
private fun HomeContent(
    loading: MutableState<Boolean>,
    error: MutableState<Boolean>,
    listBookUI: SnapshotStateList<BookUI>,
) {
    val navigator = LocalNavigator.currentOrThrow
    val context = LocalContext.current
    val packageManager = context.packageManager

    Scaffold(
        topBar = { TopBar(title = "A.Reader") },
        backgroundColor = MaterialTheme.colors.primary,
        floatingActionButton = {
            Row {
//                FloatButton(
//                    Icons.Default.Apps
//                ) {
//                    val intent = Intent(
//                        Intent.ACTION_VIEW,
//                        Uri.parse("com.example.movieapp")
//                    )
//                    intent.putExtra("deeplink", "deeplink is working")
//                    val pendingIntent = TaskStackBuilder.create(context).run {
//                        addNextIntentWithParentStack(intent)
//                        getPendingIntent(
//                            0,
//                            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
//                        )
//                    }
//                    pendingIntent.send()
//
//                    //1 tentativa
////                    val weatherApp = packageManager.getLaunchIntentForPackage("com.example.movieapp")
////                    weatherApp?.putExtra("data", "This from Reader App")
////                    if (weatherApp != null) {
////                        startActivity(context, weatherApp, Bundle())
////                    } else {
////                        Toast.makeText(context, "Is not Success", Toast.LENGTH_LONG).show()
////                    }
//                }
//                Spacer(modifier = Modifier.width(10.dp))
                FloatButton(icon = Icons.Default.Add) {
                    navigator.push(SearchScreen)
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

                Button(
                    onClick = {
                        navigator.replace(OpenbankingScreen(""))
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green.copy(alpha = 0.5f)),
                ) {
                    Text(
                        text = "Open Banking",
                        color = Color.White
                    )
                }

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

