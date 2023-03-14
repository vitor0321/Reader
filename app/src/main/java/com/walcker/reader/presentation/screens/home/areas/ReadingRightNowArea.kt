package com.walcker.reader.presentation.screens.home.areas

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.walcker.core.model.BookUI
import com.walcker.reader.presentation.components.CardBook
import com.walcker.reader.presentation.components.ErrorReader
import com.walcker.reader.presentation.components.Loading

@Composable
fun ReadingRightNowArea(
    loading: MutableState<Boolean>,
    error: MutableState<Boolean>,
    books: List<BookUI>,
    onClickDetails: (String) -> Unit
) {
    val scrollState = rememberScrollState()


        if (loading.value) Loading()
        if (error.value) ErrorReader(message = "Humm... We don't get any books.. add someone...")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(280.dp)
            .horizontalScroll(scrollState)
    ) {
        books.map {
            CardBook(it) { book ->
                onClickDetails(book)
            }
        }
    }
}