package com.walcker.reader.data.network.repository

import com.walcker.domain.data.repository.BookRepositoryRemote
import com.walcker.domain.model.BookUI
import com.walcker.reader.data.models.mapper.ResponseItemMapper
import com.walcker.reader.data.network.BooksApi
import javax.inject.Inject

class BookRepositoryRemoteImpl @Inject constructor(
    private val api: BooksApi
): BookRepositoryRemote {

    override suspend fun getBooks(searchQuery: String): List<BookUI>{

        return api.getAllBooks(searchQuery).items.map {
            ResponseItemMapper.mapToDomain(it)
        }
    }

    override suspend fun getBooksId(searchQueryId: String): BookUI{
        return ResponseItemMapper.mapToDomain(api.getBookInfo(searchQueryId))
    }
}