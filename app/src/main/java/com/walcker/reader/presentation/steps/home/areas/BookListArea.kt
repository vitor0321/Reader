package com.walcker.reader.presentation.steps.home.areas

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.walcker.domain.model.BookUI
import com.walcker.reader.presentation.components.CardBook
import com.walcker.reader.presentation.components.ErrorReader
import com.walcker.reader.presentation.components.Loading

@Composable
fun BookListArea(
    loading: MutableState<Boolean>,
    error: MutableState<Boolean>,
    listOfBooks: List<BookUI>,
    onCardPressed: (String) -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val addedBook = listOfBooks.filter {
        it.startedReading == null && it.finishedReading == null
    }

    when {
        loading.value -> Loading()
        error.value -> ErrorReader(message = "Humm... We don't get any books.. add someone...")
        listOfBooks.isNotEmpty() -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(280.dp)
                    .horizontalScroll(scrollState)
            ) {
                addedBook.map {book->
                    CardBook(book) {
                        onCardPressed(it)
                    }
                }
            }
        }
    }
}