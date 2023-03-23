package com.walcker.reader.view.steps.search.areas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.walcker.reader.domain.model.BookUI
import com.walcker.reader.view.components.CardBookSearch
import com.walcker.reader.view.components.Loading
import com.walcker.reader.view.steps.search.SearchViewModel
import com.walcker.reader.view.utils.uiState.UiStateListBooks

@Composable
internal fun SearchListOfBooks(
    viewModel: SearchViewModel,
    onClickDetails: (String) -> Unit = {}
) {
    val listBookUI = remember { mutableStateListOf<BookUI>() }
    val loading = remember { mutableStateOf(false) }
    val error = remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()

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
               (state as UiStateListBooks.SuccessAllBooks).booksList.map { book ->
                   book?.let {
                       listBookUI.add(it)
                   }
                }
                loading.value = false
                error.value = false
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (loading.value) Loading()
        if (error.value) Text(text = "Hummm .... something is wrong, try again")

        if (listBookUI.isNotEmpty()){
            LazyColumn {
                items(items = listBookUI) {
                    CardBookSearch(bookUI = it) { book ->
                        onClickDetails(book)
                    }
                }
            }
        }
    }
}