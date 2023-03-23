package com.walcker.reader.view.steps.home.areas

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.walcker.reader.domain.model.BookUI
import com.walcker.reader.view.components.CardBook
import com.walcker.reader.view.components.ErrorReader
import com.walcker.reader.view.components.Loading

@Composable
internal fun ReadingRightNowArea(
    loading: MutableState<Boolean>,
    error: MutableState<Boolean>,
    books: List<BookUI>,
    onClickDetails: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    val readingNowList = books.filter {
        it.startedReading != null && it.finishedReading == null
    }

    when {
        loading.value -> Loading()
        error.value -> ErrorReader(message = "Humm... We don't get any books.. add someone...")
        readingNowList.isNotEmpty() -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(280.dp)
                    .horizontalScroll(scrollState)
            ) {
                readingNowList.map {
                    CardBook(it) { book ->
                        onClickDetails(book)
                    }
                }
            }
        }
    }
}