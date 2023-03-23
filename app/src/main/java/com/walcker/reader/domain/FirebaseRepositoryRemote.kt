package com.walcker.reader.domain

import com.walcker.reader.domain.model.BookUI

interface FirebaseRepositoryRemote {

    suspend fun getAllBooksFromDatabase(): List<BookUI?>

}