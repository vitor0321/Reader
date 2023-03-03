package com.walcker.core.data.repository

import com.walcker.core.model.BookUI

interface BookRepositoryRemote {

    suspend fun getBooks(searchQuery: String): List<BookUI>

    suspend fun getBooksId(searchQueryId: String): BookUI
}