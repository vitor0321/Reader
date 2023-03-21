package com.walcker.domain.data.repository

import com.walcker.domain.model.BookUI

interface BookRepositoryRemote {

    suspend fun getBooks(searchQuery: String): List<BookUI>

    suspend fun getBooksId(searchQueryId: String): BookUI
}