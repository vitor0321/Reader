package com.walcker.reader.framework.network.repository

import com.walcker.core.data.repository.BookRepositoryRemote
import com.walcker.core.model.BookUI
import com.walcker.reader.framework.network.BooksApi
import com.walcker.reader.framework.network.response.toBookUI
import javax.inject.Inject

class BookRepositoryRemoteImpl @Inject constructor(
    private val api: BooksApi
): BookRepositoryRemote {

    override suspend fun getBooks(searchQuery: String): List<BookUI>{
        return api.getAllBooks(searchQuery).items.map {
            it.toBookUI()
        }
    }

    override suspend fun getBooksId(searchQueryId: String): BookUI{
        return api.getBookInfo(searchQueryId).toBookUI()
    }
}