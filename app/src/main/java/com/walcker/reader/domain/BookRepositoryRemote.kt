package com.walcker.reader.domain

import com.walcker.reader.domain.model.BookUI

interface BookRepositoryRemote {

    suspend fun getBooks(searchQuery: String): List<BookUI>

    suspend fun getBooksId(searchQueryId: String): BookUI
}