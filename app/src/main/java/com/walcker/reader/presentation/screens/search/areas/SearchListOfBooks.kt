package com.walcker.reader.presentation.screens.search.areas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import com.walcker.core.model.BookUI
import com.walcker.reader.presentation.components.CardBookSearch
import com.walcker.reader.presentation.screens.search.SearchViewModel

@Composable
fun SearchListOfBooks(
    viewModel:SearchViewModel,
    onClickDetails: (String) -> Unit = {}
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val listBookUI= remember { mutableStateListOf<BookUI>() }
    val loading = remember { mutableStateOf(false) }

    viewModel.state.observe(lifecycleOwner) { uiState ->
        when (uiState) {
            is SearchViewModel.UiState.Loading -> {
                loading.value = true
            }
            is SearchViewModel.UiState.Error -> {
                loading.value = false
            }
            is SearchViewModel.UiState.SuccessAllBooks -> {
                uiState.booksList.map {book->
                    listBookUI.add(book)
                }
                loading.value = false
            }
            is SearchViewModel.UiState.SuccessBookId -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (loading.value){
            CircularProgressIndicator(
                color = Color.Red,
                strokeWidth = 5.dp
            )
        }

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