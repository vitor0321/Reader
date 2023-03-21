package com.walcker.domain.data.repository

import com.walcker.domain.model.BookUI

interface FirebaseRepositoryRemote {

    suspend fun getAllBooksFromDatabase(): List<BookUI?>

}