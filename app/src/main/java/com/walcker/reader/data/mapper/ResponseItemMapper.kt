package com.walcker.reader.data.mapper

import com.walcker.reader.domain.model.BookUI
import com.walcker.reader.data.models.BookUIFirebase
import com.walcker.reader.data.models.Item

internal object ResponseItemMapper {

    fun mapToData(response: Item): BookUI = with(response) {
        BookUI(
            id = id,
            title = volumeInfo.title,
            authors = if (volumeInfo.authors != null) volumeInfo.authors[0] else "",
            description = volumeInfo.description,
            date = volumeInfo.publishedDate,
            category = if (volumeInfo.categories != null) volumeInfo.categories[0] else "",
            photoUrl = volumeInfo.imageLinks.thumbnail.replace("http", "https"),
            pageCount = volumeInfo.pageCount
        )
    }

    fun mapToData(bookUIFirebase: BookUIFirebase): BookUI = with(bookUIFirebase) {
        BookUI(
            id = id,
            title = title,
            authors = authors ?: "",
            notes = notes,
            date = date,
            category = category ?: "",
            photoUrl = photoUrl,
            rating = rating,
            description = description ?: "",
            pageCount = pageCount,
            startedReading = startedReading,
            finishedReading = finishedReading,
            userId = userId,
            googleBookId = googleBookId
        )
    }

    fun mapToData(bookUI: BookUI): BookUIFirebase = with(bookUI) {
        BookUIFirebase(
            id = id,
            title = title,
            authors = authors ?: "",
            notes = notes,
            date = date,
            category = category ?: "",
            photoUrl = photoUrl,
            rating = rating,
            description = description ?: "",
            pageCount = pageCount,
            startedReading = startedReading,
            finishedReading = finishedReading,
            userId = userId,
            googleBookId = googleBookId
        )
    }
}