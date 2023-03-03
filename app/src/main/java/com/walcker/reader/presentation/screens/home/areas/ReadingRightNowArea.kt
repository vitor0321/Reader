package com.walcker.reader.presentation.screens.home.areas

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.walcker.core.model.BookUI
import com.walcker.reader.presentation.components.CardBook

@Composable
fun ReadingRightNowArea(
    books: List<BookUI>,
    onClickDetails: (String) -> Unit = {}
) {
    val scrollState = rememberScrollState()

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