package com.walcker.reader.data.network

import com.walcker.reader.domain.BookRepositoryRemote
import com.walcker.reader.domain.model.BookUI
import com.walcker.reader.data.mapper.ResponseItemMapper

class BookRepositoryRemoteImpl(
    private val api: BooksApi
): BookRepositoryRemote {

    override suspend fun getBooks(searchQuery: String): List<BookUI>{

        return api.getAllBooks(searchQuery).items.map {
            ResponseItemMapper.mapToData(it)
        }
    }

    override suspend fun getBooksId(searchQueryId: String): BookUI {
        return ResponseItemMapper.mapToData(api.getBookInfo(searchQueryId))
    }
}