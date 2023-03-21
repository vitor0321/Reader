package com.walcker.reader.data.models.mapper

import com.walcker.domain.model.BookUI
import com.walcker.reader.data.models.response.Item

internal object ResponseItemMapper {

    fun mapToDomain(response: Item): BookUI = with(response){
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
}