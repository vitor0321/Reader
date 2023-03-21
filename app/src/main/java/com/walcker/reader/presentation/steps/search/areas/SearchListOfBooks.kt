package com.walcker.reader.presentation.steps.search.areas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.walcker.domain.model.BookUI
import com.walcker.reader.presentation.components.CardBookSearch
import com.walcker.reader.presentation.components.Loading
import com.walcker.reader.presentation.steps.search.SearchViewModel

@Composable
fun SearchListOfBooks(
    viewModel: SearchViewModel = hiltViewModel(),
    onClickDetails: (String) -> Unit = {}
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val listBookUI = remember { mutableStateListOf<BookUI>() }
    val loading = remember { mutableStateOf(false) }
    val error = remember { mutableStateOf(false) }

    viewModel.state.observe(lifecycleOwner) { uiState ->
        when (uiState) {
            is SearchViewModel.UiState.Loading -> {
                loading.value = true
                error.value = false
            }
            is SearchViewModel.UiState.Error -> {
                loading.value = false
                error.value = true
            }
            is SearchViewModel.UiState.SuccessAllBooks -> {
                uiState.booksList.map { book ->
                    listBookUI.add(book)
                }
                loading.value = false
                error.value = false
            }
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