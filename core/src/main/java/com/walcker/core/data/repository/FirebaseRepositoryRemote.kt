package com.walcker.core.data.repository

import com.walcker.core.model.BookUI

interface FirebaseRepositoryRemote {

    suspend fun getAllBooksFromDatabase(): List<BookUI?>

}